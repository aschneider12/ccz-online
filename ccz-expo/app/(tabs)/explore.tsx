import { showAlert } from '@/components/alert/AlertService';
import MapNative from '@/components/MapNative';
import { API_URLS } from '@/config/api';
import { useUser } from '@/context/context';
import * as Location from 'expo-location';
import { router } from 'expo-router';

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

       setLoading(true);

       setRegion({
          latitude:  latitude,
          longitude: longitude,
          latitudeDelta: 0.0025,
          longitudeDelta: 0.0025,
       });

       setMarkers([{
          id: 'current',
          latitude,
          longitude,
          title: 'Você está aqui!',
          description: 'Clique no mapa para adicionar um alerta!',
        }]);

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

          if(data.length > 0) {
            setMarkers(novosMarkers);
            showAlert("Alertas na região", "Atenção, foram encontrados "+data.length+ " alertas na sua região", 'alert');
          } else {
            showAlert("Nenhum alerta na região", "Que bom, nada encontrado nessa área.", 'info');
          }      

        } else {

          console.log('status response', response.status)
          console.log('response completa', response)
          showAlert("Problema no back", "Não foi possível buscar os alertas", 'alert');
        }
  
      } catch (error) {
        console.log(error)
        showAlert("Erro", 'Nada foi encontrado na região', 'error');
      }
    };

  const [region, setRegion] = useState({});

  const onClickOnMap = (event) => {

    const latitude = event.latitude;
    const longitude = event.longitude;

    if(user?.perfil == 'CIDADAO') {
    
      router.push({
        pathname: '/cadastroSolicitacao',
        params: {lat: latitude, lng: longitude}
      })
    } else {

      router.push({
        pathname: '/cadastrarAlerta',
        params: {lat: latitude, lng: longitude}
      })
    }
  };

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
        onClickOnMap={onClickOnMap}
        markers={markers}
        showsUserLocation={true}
        followsUserLocation={false}
      />
    </View>
  )};


const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
}); 

export default Explore;