import React, { useEffect, useRef, useState } from 'react';
import { StyleSheet, View } from 'react-native';

interface MapWebProps {
  initialRegion?: {
    latitude: number;
    longitude: number;
    latitudeDelta: number;
    longitudeDelta: number;
  };

  onRegionChange?: (region: {
    latitude: number;
    longitude: number;
    latitudeDelta: number;
    longitudeDelta: number;
  }) => void;
  onClickOnMap?: (event: {}) => void;

  markers?: Array<{
    id: string;
    latitude: number;
    longitude: number;
    title?: string;
    description?: string;
  }>;
  showsUserLocation?: boolean;
  followsUserLocation?: boolean;
}

const MapWeb: React.FC<MapWebProps> = ({
  initialRegion = {
    latitude: -22.2232,
    longitude: -54.8086,
    latitudeDelta: 0.0922,
    longitudeDelta: 0.0421,
  },
  onRegionChange,
  onClickOnMap,
  markers = [],
  showsUserLocation = true,
  followsUserLocation = false,
}) => {
  const mapContainerRef = useRef<HTMLDivElement>(null);
  const mapRef = useRef<google.maps.Map | null>(null);
  const markersRef = useRef<google.maps.Marker[]>([]);
  const [isLoaded, setIsLoaded] = useState(false);

  // Carregar Google Maps API
  useEffect(() => {
    const loadGoogleMaps = () => {
      if (typeof window === 'undefined') return;

      // Verificar se já foi carregado
      if (window.google && window.google.maps) {
        setIsLoaded(true);
        return;
      }

      // Criar script tag
      const script = document.createElement('script');
      script.src = `https://maps.googleapis.com/maps/api/js?key=${process.env.EXPO_PUBLIC_GOOGLE_MAPS_API_KEY}`;
      script.async = true;
      script.defer = true;
      script.onload = () => setIsLoaded(true);
      document.head.appendChild(script);
    };

    loadGoogleMaps();
  }, []);

  // Inicializar mapa
  useEffect(() => {
    if (!isLoaded || !mapContainerRef.current) return;

    const zoom = Math.round(Math.log(360 / initialRegion.longitudeDelta) / Math.LN2);

    mapRef.current = new google.maps.Map(mapContainerRef.current, {
      center: {
        lat: initialRegion.latitude,
        lng: initialRegion.longitude,
      },
      zoom: zoom,
      mapTypeControl: true,
      streetViewControl: true,
      fullscreenControl: true,
      zoomControl: true,
    });

    mapRef.current.addListener("click", (e) => {
      onClickOnMap?.({
        latitude: e.latLng.lat(),
        longitude: e.latLng.lng(),
      });
    });

    // Listener para mudanças de região
    mapRef.current.addListener('idle', () => {
      if (!mapRef.current) return;

      const center = mapRef.current.getCenter();
      const bounds = mapRef.current.getBounds();
      
      if (!center || !bounds) return;

      const ne = bounds.getNorthEast();
      const sw = bounds.getSouthWest();

      const latitudeDelta = ne.lat() - sw.lat();
      const longitudeDelta = ne.lng() - sw.lng();

      onRegionChange?.({
        latitude: center.lat(),
        longitude: center.lng(),
        latitudeDelta,
        longitudeDelta,
      });
    });

    // Localização do usuário
    if (showsUserLocation && navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const userLocation = {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          };

          new google.maps.Marker({
            position: userLocation,
            map: mapRef.current!,
            title: 'Sua localização',
            icon: {
              path: google.maps.SymbolPath.CIRCLE,
              scale: 8,
              fillColor: '#4285F4',
              fillOpacity: 1,
              strokeColor: '#ffffff',
              strokeWeight: 2,
            },
          });

          if (followsUserLocation) {
            mapRef.current?.setCenter(userLocation);
          }
        },
        (error) => {
          console.error('Erro ao obter localização:', error);
        }
      );
    }
  }, [isLoaded, initialRegion, showsUserLocation, followsUserLocation]);

  // Atualizar marcadores
  useEffect(() => {
    if (!mapRef.current) return;

    // Remover marcadores antigos
    markersRef.current.forEach((marker) => marker.setMap(null));
    markersRef.current = [];

    // Adicionar novos marcadores
    markers.forEach((markerData) => {
      const marker = new google.maps.Marker({
        position: {
          lat: markerData.latitude,
          lng: markerData.longitude,
        },
        map: mapRef.current!,
        title: markerData.title,
      });

      if (markerData.description) {
        const infoWindow = new google.maps.InfoWindow({
          content: `
            <div style="padding: 8px;">
              <h3 style="margin: 0 0 8px 0; font-size: 16px;">${markerData.title || ''}</h3>
              <p style="margin: 0; font-size: 14px;">${markerData.description}</p>
            </div>
          `,
        });

        marker.addListener('click', () => {
          infoWindow.open(mapRef.current!, marker);
        });
      }

      markersRef.current.push(marker);
    });
  }, [markers, isLoaded]);

  if (!isLoaded) {
    return (
      <View style={styles.container}>
        <View style={styles.loading}>
          <p>Carregando mapa...</p>
        </View>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <div ref={mapContainerRef} style={webStyles.map} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  loading: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

const webStyles = {
  map: {
    width: '100%',
    height: '100%',
  },
};

export default MapWeb;