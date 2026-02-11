import React, { useRef, useState } from 'react';
import { Dimensions, StyleSheet, View } from 'react-native';
import MapView, { Marker, PROVIDER_GOOGLE } from 'react-native-maps';

interface MapNativeProps {
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

const MapNative: React.FC<MapNativeProps> = ({
  initialRegion = {
    latitude: -22.2232,
    longitude: -54.8086,
    latitudeDelta: 0.0922,
    longitudeDelta: 0.0421,
  },
  onRegionChange,
  markers = [],
  showsUserLocation = true,
  followsUserLocation = false,
}) => {
  const mapRef = useRef<MapView>(null);
  const [region, setRegion] = useState(initialRegion);

  const handleRegionChangeComplete = (newRegion: {
    latitude: number;
    longitude: number;
    latitudeDelta: number;
    longitudeDelta: number;
  }) => {
    setRegion(newRegion);
    onRegionChange?.(newRegion);
  };

  return (
    <View style={styles.container}>
      <MapView
        ref={mapRef}
        style={styles.map}
        provider={PROVIDER_GOOGLE}
        initialRegion={initialRegion}
        onRegiondChangeComplete={handleRegionChangeComplete}
        showsUserLocation={showsUserLocation}
        followsUserLocation={followsUserLocation}
        showsMyLocationButton={true}
        onPress={(event) => {
          const { latitude, longitude } = event.nativeEvent.coordinate;
          console.log("Clicou em:", latitude, longitude);
        }}
        showsCompass={true}
        showsScale={true}
        zoomEnabled={true}
        scrollEnabled={true}
        pitchEnabled={true}
        rotateEnabled={true}
      >
        {markers.map((marker) => (
          <Marker
            key={marker.id}
            coordinate={{
              latitude: marker.latitude,
              longitude: marker.longitude,
            }}
            title={marker.title}
            description={marker.description}
          />
        ))}
      </MapView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  map: {
    width: Dimensions.get('window').width,
    height: Dimensions.get('window').height,
  },
});

export default MapNative;