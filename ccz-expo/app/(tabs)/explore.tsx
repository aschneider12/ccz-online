import MapNative from '@/components/MapNative';

import React, { useState } from 'react';
import { StyleSheet, View } from 'react-native';

const Explore = () => {
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
      description: 'Sua localização',
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