import { HeaderMenu } from '@/components/HeaderMenu';
import { Ionicons } from '@expo/vector-icons';
import { router, Tabs } from "expo-router";

export default function TabsLayout() {
  return (

    <Tabs
      screenOptions={{
        headerShown: true,
        tabBarActiveTintColor: '#3498DB',
        tabBarInactiveTintColor: '#95A5A6',
        headerRight: () => <HeaderMenu />
      }} >
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
        name="explore" 
        options={{ 
          title: "Alertas",
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="map-outline" size={size} color={color} />
          ),
          headerLeft: () => (
            <Ionicons 
              name="arrow-back" 
              size={24} 
              color="#3498DB" 
              onPress={() => router.back()} // Se estiver usando expo-router
              style={{ marginLeft: 15 }} 
            />
          ),
        }} 
      />
    </Tabs>
  );
}

