import React from 'react';
import MapWeb from './MapWeb';

// No web, MapNative simplesmente retorna MapWeb
const MapNative: React.FC<any> = (props) => {
  return <MapWeb {...props} />;
};

export default MapNative;