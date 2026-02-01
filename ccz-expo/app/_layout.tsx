import { AlertProvider } from "@/components/alert/AlertProvider";
import { Stack } from "expo-router";
import { UserProvider } from "../context/context";

export default function RootLayout() {
  return (
    <AlertProvider>
      <UserProvider>
        <Stack screenOptions={{headerShown: true}}/>
      </UserProvider>
    </AlertProvider>
  );
}
