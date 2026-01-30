import { Stack } from "expo-router";
import { UserProvider } from "../context/context";

export default function RootLayout() {
  return (
    <UserProvider>
      <Stack />
    </UserProvider>
  );
}
