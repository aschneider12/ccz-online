import React, { useState } from 'react';
import { StatusBar, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native';

export default function HomeScreen() {

  const [cpf, setCpf] = useState('');

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
    console.log('Login via GOV.BR');
  };

  const handleDirectLogin = () => {
    console.log('Login direto com CPF:', cpf);
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
      
      <TouchableOpacity 
        style={styles.buttonPrimary}
        onPress={handleGovBrLogin}
      >
        <Text style={styles.buttonPrimaryText}>Login via GOV.BR</Text>
      </TouchableOpacity>
      
      <TouchableOpacity 
        style={styles.buttonSecondary}
        onPress={handleDirectLogin}
      >
        <Text style={styles.buttonSecondaryText}>ENTRAR</Text>
      </TouchableOpacity>
    </View>
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
    backgroundColor: '#1a1a1a',
    borderRadius: 30,
    borderWidth: 2,
    borderColor: '#ffffff',
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