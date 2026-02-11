import { showAlert } from '@/components/alert/AlertService';
import SharedButton from '@/components/SharedButton';
import { API_URLS } from '@/config/api';
import { useUser } from '@/context/context';
import { Stack, useRouter } from 'expo-router';
import React, { useState } from 'react';
import { StatusBar, StyleSheet, Text, TextInput, View } from "react-native";

export default function IndexScreen() {

  const context = useUser();
      
  const router = useRouter();

  const [cpf, setCpf] = useState('111.111.111-11');

   const [alertVisible, setAlertVisible] = useState(false);

  const formatCPF = (text) => {
    const cleaned = text.replace(/\D/g, '');
    const limited = cleaned.substring(0, 11);
    
    if (limited.length <= 3) return limited;
    if (limited.length <= 6) return `${limited.slice(0, 3)}.${limited.slice(3)}`;
    if (limited.length <= 9) return `${limited.slice(0, 3)}.${limited.slice(3, 6)}.${limited.slice(6)}`;
    return `${limited.slice(0, 3)}.${limited.slice(3, 6)}.${limited.slice(6, 9)}-${limited.slice(9)}`;
  };

  const handleCPFChange = (text) => {
    setCpf(formatCPF(text));
  };

  const handleGovBrLogin = () => {
    showAlert("Ainda não foi implementada a integração!", 'Atenção', 'alert');
  };

  const handleCriarUsuario = () => {
    router.push("/criarUsuario");
  }

  const handleDirectLogin = async () => {
    try {
      
      const response = await fetch(API_URLS.AUTH.LOGIN,
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
  
      if (!response.ok) {
        console.log('resposta nao ok')
        throw new Error(text);
      } 

      const data = JSON.parse(text);
      console.log("Login OK:", data);
      
      context.setUser(data);
      router.replace("/home");
      // redireciona para nova tela
    
    } catch (error: any) {
      
      showAlert("Problema no login", "Contate o suporte, back parado!", 'alert');
      
    }
  };

  return (

    <View style={styles.container} >
    <StatusBar barStyle="light-content" />

    <Stack.Screen options={{ title: 'CCZ Online' }} />

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
      <SharedButton title='Login via GOV.BR' onPress={handleGovBrLogin} disabled></SharedButton>

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