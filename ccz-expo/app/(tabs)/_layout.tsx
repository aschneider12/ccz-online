import { Ionicons } from '@expo/vector-icons';
import { Tabs } from "expo-router";

export default function TabsLayout() {
  return (

    <Tabs
      screenOptions={{
        tabBarActiveTintColor: '#3498DB',
        tabBarInactiveTintColor: '#95A5A6',
      }}
  >
      <Tabs.Screen 
        name="home" 
        options={{ 
          title: "Início",
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="home" size={size} color={color} />
          ),
        }} 
      />
        <Tabs.Screen 
          name="cadastroSolicitacao" 
          options={{ 
            title: "Solicitacao",
            tabBarIcon: ({ color, size }) => (
              <Ionicons name="map-outline" size={size} color={color} />
            ),
          }} 
        />
      <Tabs.Screen 
        name="explore" 
        options={{ 
          title: "Região",
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="compass" size={size} color={color} />
          ),
        }} 
      />
    </Tabs>
  );
}

