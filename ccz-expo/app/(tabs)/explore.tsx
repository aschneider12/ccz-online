import { showAlert } from '@/components/alert/AlertService';
import MapNative from '@/components/MapNative';
import { API_URLS } from '@/config/api';
import { useUser } from '@/context/context';

import React, { useEffect, useState } from 'react';
import { StyleSheet, View } from 'react-native';

const Explore = () => {

  const { user } = useUser(); 

  useEffect(() => {
    buscarSolicitacoesProximas();
  }, []);

  const buscarSolicitacoesProximas = async () => {
      try {
  
        // Substitua pela chamada real à sua API
        const response = await fetch(API_URLS.USUARIOS.SOLICITACOES_PROXIMAS, 

        );
        const data = await response.json();
        setMunicipios(data);
  
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
  };

  // Exemplo de marcadores (opcional)
  const markers = [
    {
      id: '1',
      latitude: -22.2232,
      longitude: -54.8086,
      title: 'Dourados, MS',
      description: 'Sua localização atual aqui',
    },
  ];

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