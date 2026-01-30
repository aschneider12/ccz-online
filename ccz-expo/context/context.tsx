import { User } from "../interfaces/IUser";
import React, {
  createContext,
  useContext,
  useState,
  ReactNode,
} from "react";

interface UserContextData {
  user: User | null;
  setUser: (user: User | null) => void;
}

const UserContext = createContext<UserContextData | undefined>(undefined);

interface UserProviderProps {
  children: ReactNode;
}

export function UserProvider({ children }: UserProviderProps) {
  const [user, setUser] = useState<User | null>(null);

  return (
    <UserContext.Provider value={{ user, setUser }}>
      {children}
    </UserContext.Provider>
  );
}

export function useUser(): UserContextData {
  const context = useContext(UserContext);

  if (!context) {
    throw new Error("useUser must be used within a UserProvider");
  }

  return context;
}
