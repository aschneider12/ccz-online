import SharedButton from '@/components/SharedButton';
import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import { Alert, StatusBar, StyleSheet, Text, TextInput, View } from "react-native";
import { useUser } from '../context/context';

export default function IndexScreen() {

  const router = useRouter();
  const [cpf, setCpf] = useState('111.111.111-11');

  const { setUser } = useUser();

  const formatCPF = (text: string) => {
    const cleaned = text.replace(/\D/g, '');
    const limited = cleaned.substring(0, 11);
    
    if (limited.length <= 3) return limited;
    if (limited.length <= 6) return `${limited.slice(0, 3)}.${limited.slice(3)}`;
    if (limited.length <= 9) return `${limited.slice(0, 3)}.${limited.slice(3, 6)}.${limited.slice(6)}`;
    return `${limited.slice(0, 3)}.${limited.slice(3, 6)}.${limited.slice(6, 9)}-${limited.slice(9)}`;
  };

  const handleCPFChange = (text: string) => {
    setCpf(formatCPF(text));
  };

  const handleGovBrLogin = () => {
    console.log('Login via GOV.BR');
  };

  const handleCriarUsuario = () => {
    router.push("/criarUsuario");
  }

  const handleDirectLogin = async () => {
    try {
      
      console.log('fazendo request')
      const response = await fetch(
        "http://127.0.0.1:8080/api/v1/auth/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            cpf: cpf
          }),
        }
      );
  
      const text = await response.text(); 
      console.log("Status:", response.status);
      console.log("Resposta do backend:", text);
  
      if (!response.ok) {
        throw new Error(text);
      }
  
      const data = JSON.parse(text);
      console.log("Login OK:", data);
  
      console.log("Login realizado:", data);

      setUser(data);
      
  
      router.push("/home");
      // redireciona para nova tela
    
    } catch (error: any) {
      Alert.alert("Erro no login", error.message);
    }
  };

  return (

    <View style={styles.container}>
    <StatusBar barStyle="light-content" />
    
    <View style={styles.card}>
      <Text style={styles.title}>CCZ Online</Text>
      
      <Text style={styles.subtitle}>Login</Text>
      
      <TextInput
        style={styles.input}
        placeholder="digite seu CPF"
        placeholderTextColor="#888"
        value={cpf} 
        onChangeText={handleCPFChange}
        keyboardType="numeric"
        maxLength={14}
      />
      
      <SharedButton title='ENTRAR' onPress={handleDirectLogin}></SharedButton>
      <SharedButton title='Login via GOV.BR' onPress={handleGovBrLogin}></SharedButton>

    </View>
      <SharedButton title='Criar usuário' onPress={handleCriarUsuario}></SharedButton>
  </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0a0a0a',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  card: {
    backgroundColor: '#1a1a17',
    borderRadius: 30,
    borderWidth: 2,
    borderColor: '#fffff',
    padding: 40,
    width: '100%',
    maxWidth: 400,
    alignItems: 'center',
  },
  title: {
    fontSize: 28,
    fontWeight: '600',
    color: '#ffffff',
    marginBottom: 30,
    textAlign: 'center',
  },
  subtitle: {
    fontSize: 18,
    color: '#ffffff',
    marginBottom: 25,
    textAlign: 'center',
  },
  input: {
    width: '100%',
    backgroundColor: 'transparent',
    borderWidth: 2,
    borderColor: '#ffffff',
    borderRadius: 8,
    padding: 15,
    fontSize: 16,
    color: '#ffffff',
    marginBottom: 20,
    textAlign: 'center',
  },
  buttonPrimary: {
    width: '100%',
    backgroundColor: '#5b9ceb',
    borderRadius: 8,
    padding: 15,
    alignItems: 'center',
    marginBottom: 15,
  },
  buttonPrimaryText: {
    color: '#000000',
    fontSize: 16,
    fontWeight: '600',
  },
  buttonSecondary: {
    width: '100%',
    backgroundColor: '#6ba3f0',
    borderRadius: 8,
    padding: 15,
    alignItems: 'center',
  },
  buttonSecondaryText: {
    color: '#000000',
    fontSize: 16,
    fontWeight: '600',
  },
});