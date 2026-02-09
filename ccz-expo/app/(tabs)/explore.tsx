import { showAlert } from '@/components/alert/AlertService';
import MapNative from '@/components/MapNative';
import { API_URLS } from '@/config/api';
import { useUser } from '@/context/context';
import * as Location from 'expo-location';

import React, { useEffect, useState } from 'react';
import { StyleSheet, View } from 'react-native';

type MarkerType = {
  id: string;
  latitude: number;
  longitude: number;
  title?: string;
  description?: string;
};

const Explore = () => {
  
  const [loading, setLoading] = useState(true);

  const { user } = useUser(); 

  const [markers, setMarkers] = useState([]);

  useEffect(() => {

    (async () => {

      const { status } = await Location.requestForegroundPermissionsAsync();

      if (status !== 'granted') {
        console.log('Permissão negada');
        setLoading(false);
        return;
      }
      // 2. Pegar localização atual
      const location = await Location.getCurrentPositionAsync({
        accuracy: Location.Accuracy.High,
      });

       const { latitude, longitude } = location.coords;

       setLoading(false);

        setMarkers([{
          id: 'current',
          latitude,
          longitude,
          title: 'Você está aqui',
          description: 'Localização atual',
        }  ]);

      buscarSolicitacoesProximas(latitude, longitude);

     })();
  

    

  }, []);

  const buscarSolicitacoesProximas = async (latitude, longitude) => {

      try {

        console.log('buscando solicitacoes', latitude, longitude);

        const params = new URLSearchParams({
          perfil: user?.perfil,
          latitude:  latitude,
          longitude: longitude,
          distancia : 5000,
        }).toString();

        // Substitua pela chamada real à sua API
        const response = await fetch(`${API_URLS.ALERTAS.PROXIMOS}?${params}`);

        if(response.ok) {

          const data = await response.json();
          console.log('retorno do back', data)

          const novosMarkers = data.map(item => ({
            id : item.id, 
            latitude: item.latitude,
             longitude: item.longitude,
             title : item.titulo,
             description: item.descricao
          }));

          setMarkers(novosMarkers);

        } else {

          console.log('status response', response.status)
          console.log('response completa', response)
        }
  
      } catch (error) {

        showAlert("Nenhum alerta", 'Nada foi encontrado na região', 'success');
      }
    };

  const [region, setRegion] = useState({
    latitude: -22.2232,
    longitude: -54.8086,
    latitudeDelta: 0.0922,
    longitudeDelta: 0.0421,
  });

  const handleRegionChange = (newRegion: {
    latitude: number;
    longitude: number;
    latitudeDelta: number;
    longitudeDelta: number;
  }) => {
    // setRegion(newRegion);
    console.log('Região alterada:', newRegion);
    // buscarSolicitacoesProximas(newRegion.latitude,newRegion.longitude);
  };

  return (
    <View style={styles.container}>
      <MapNative
        initialRegion={region}
        onRegionChange={handleRegionChange}
        markers={markers}
        showsUserLocation={true}
        followsUserLocation={false}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default Explore;