import { showAlert } from '@/components/alert/AlertService';
import { API_URLS } from '@/config/api';
import { Ionicons } from '@expo/vector-icons';
import { Picker } from '@react-native-picker/picker';
import { router } from 'expo-router';
import React, { useState } from 'react';
import {
  ActivityIndicator,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View
} from 'react-native';



const CadastroUsuarioScreen = () => {


  const [formData, setFormData] = useState({
    nome: '',
    cpf: '',
    cartaoSus: '',
    perfil: 'CIDADAO'
  });
  
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});

  const perfis = [
    { label: 'Cidadão', value: 'CIDADAO' },
    { label: 'Agente CCZ', value: 'AGENTE_CCZ' },
    { label: 'Administrador', value: 'ADMIN' }
  ];

  // Função para formatar CPF
  const formatarCPF = (texto) => {
    const numeros = texto.replace(/\D/g, '');
    return numeros.slice(0, 11);
  };

  // Função para formatar Cartão SUS
  const formatarCartaoSUS = (texto) => {
    const numeros = texto.replace(/\D/g, '');
    return numeros.slice(0, 15);
  };

  // Validação do formulário
  const validarFormulario = () => {
    const novosErros = {};

    if (!formData.nome.trim()) {
      novosErros.nome = 'Nome é obrigatório';
    } else if (formData.nome.trim().length < 3) {
      novosErros.nome = 'Nome deve ter pelo menos 3 caracteres';
    }

    if (!formData.cpf) {
      novosErros.cpf = 'CPF é obrigatório';
    } else if (formData.cpf.length !== 11) {
      novosErros.cpf = 'CPF deve conter 11 dígitos';
    }

    if (!formData.cartaoSus) {
      novosErros.cartaoSus = 'Cartão SUS é obrigatório';
    } else if (formData.cartaoSus.length !== 15) {
      novosErros.cartaoSus = 'Cartão SUS deve conter 15 dígitos';
    }

    setErrors(novosErros);
    return Object.keys(novosErros).length === 0;
  };

  // Função para enviar dados para a API
  const cadastrarUsuario = async () => {
    if (!validarFormulario()) {
      showAlert('Erro de Validação', 'Por favor, corrija os erros no formulário', 'alert');
      return;
    }

    setLoading(true);

    try {

      const response = await fetch(API_URLS.USUARIOS.BASE, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nome: formData.nome,
          cpf: formData.cpf,
          cartaoSus: parseInt(formData.cartaoSus),
          perfil: formData.perfil
        }),
      });

      const data = await response.json();
      console.log(response)
      console.log(data)
      if (response.status == 201) {
       
       showAlert('Sucesso!','Usuário cadastrado com sucesso','success');
        
      } else {
        console.log('caiu no erro', data)
        // Trata erros da API
        let mensagemErro = 'Erro ao cadastrar usuário';
        
        if (response.status === 409) {
          mensagemErro = 'CPF já cadastrado no sistema';
        } else if (data.message) {
          mensagemErro = data.message;
        }

        showAlert('Atenção', mensagemErro, 'alert');
      }
    } catch (error) {
      
      console.error('Erro:', error);
      showAlert('Erro de Conexão','Não foi possível conectar ao servidor. Verifique sua conexão e tente novamente.','alert');
      
    } finally {
      setLoading(false);
    }

  };

  const limparFormulario = () => {
    setFormData({
      nome: '',
      cpf: '',
      cartaoSus: '',
      perfil: 'CIDADAO'
    });
    setErrors({});
  };

  return (
    
    <KeyboardAvoidingView
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      style={styles.container}
    >
      <ScrollView 
        contentContainerStyle={styles.scrollContent}
        showsVerticalScrollIndicator={false}
      >
        <View style={styles.header}>
          <Text style={styles.title}>Cadastro de Usuário</Text>
          <Text style={styles.subtitle}>
            Sistema de Controle de Zoonoses
          </Text>
        </View>

        <View style={styles.form}>
          {/* Campo Nome */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Nome completo *</Text>
            <TextInput
              style={[styles.input, errors.nome && styles.inputError]}
              placeholder="Digite seu nome completo"
              value={formData.nome}
              onChangeText={(text) => setFormData({ ...formData, nome: text })}
              autoCapitalize="words"
              maxLength={255}
            />
            {errors.nome && (
              <Text style={styles.errorText}>{errors.nome}</Text>
            )}
          </View>

          {/* Campo CPF */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>CPF *</Text>
            <TextInput
              style={[styles.input, errors.cpf && styles.inputError]}
              placeholder="Digite apenas números"
              value={formData.cpf}
              onChangeText={(text) => setFormData({ ...formData, cpf: formatarCPF(text) })}
              keyboardType="numeric"
              maxLength={11}
            />
            {errors.cpf && (
              <Text style={styles.errorText}>{errors.cpf}</Text>
            )}
            <Text style={styles.helperText}>
              {formData.cpf.length}/11 dígitos
            </Text>
          </View>

          {/* Campo Cartão SUS */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Cartão SUS *</Text>
            <TextInput
              style={[styles.input, errors.cartaoSus && styles.inputError]}
              placeholder="Digite os 15 dígitos"
              value={formData.cartaoSus}
              onChangeText={(text) => setFormData({ ...formData, cartaoSus: formatarCartaoSUS(text) })}
              keyboardType="numeric"
              maxLength={15}
            />
            {errors.cartaoSus && (
              <Text style={styles.errorText}>{errors.cartaoSus}</Text>
            )}
            <Text style={styles.helperText}>
              {formData.cartaoSus.length}/15 dígitos
            </Text>
          </View>

          {/* Campo Perfil */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Perfil *</Text>
            <View style={styles.pickerContainer}>
              <Picker
                selectedValue={formData.perfil}
                onValueChange={(value) => setFormData({ ...formData, perfil: value })}
                style={styles.picker}
              >
                {perfis.map((perfil) => (
                  <Picker.Item
                    key={perfil.value}
                    label={perfil.label}
                    value={perfil.value}
                  />
                ))}
              </Picker>
            </View>
          </View>

          {/* Botões */}
          <View style={styles.buttonGroup}>
            <TouchableOpacity
              style={[styles.button, styles.buttonPrimary]}
              onPress={cadastrarUsuario}
              disabled={loading}
            >
              {loading ? (
                <ActivityIndicator color="#FFF" />
              ) : (
                <Text style={styles.buttonText}>Cadastrar</Text>
              )}
            </TouchableOpacity>

            <View style={styles.coordsRow}>

              <TouchableOpacity
                style={[styles.button, styles.buttonSecondary]}
                onPress={limparFormulario}
                disabled={loading}
                >
                <Ionicons name="trash-bin" size={20}/>
                <Text style={styles.buttonTextSecondary}>Limpar</Text>
              </TouchableOpacity>

              <TouchableOpacity
                    style={[styles.button, styles.buttonSecondary]}
                    onPress={router.back}
                    disabled={loading}
                    >
                    <Ionicons name="arrow-back" size={20}/>
                    <Text style={styles.buttonTextSecondary}>Voltar</Text>
                  </TouchableOpacity>
                </View>
          </View>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
  },
  coordsRow: {
    flexDirection: 'row',
  },
  scrollContent: {
    flexGrow: 1,
    padding: 20,
  },
  header: {
    marginBottom: 30,
    marginTop: 20,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2C3E50',
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 16,
    color: '#7F8C8D',
  },
  form: {
    backgroundColor: '#FFFFFF',
    borderRadius: 12,
    padding: 20,
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 3.84,
    elevation: 5,
  },
  inputGroup: {
    marginBottom: 20,
  },
  label: {
    fontSize: 16,
    fontWeight: '600',
    color: '#2C3E50',
    marginBottom: 8,
  },
  input: {
    backgroundColor: '#F8F9FA',
    borderWidth: 1,
    borderColor: '#E0E0E0',
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
    color: '#2C3E50',
  },
  inputError: {
    borderColor: '#E74C3C',
    borderWidth: 2,
  },
  errorText: {
    color: '#E74C3C',
    fontSize: 14,
    marginTop: 4,
  },
  helperText: {
    color: '#95A5A6',
    fontSize: 12,
    marginTop: 4,
  },
  pickerContainer: {
    backgroundColor: '#F8F9FA',
    borderWidth: 1,
    borderColor: '#E0E0E0',
    borderRadius: 8,
    overflow: 'hidden',
  },
  picker: {
    height: 50,
  },
  buttonGroup: {
    marginTop: 10,
  },
  button: {
    borderRadius: 8,
    padding: 16,
    alignItems: 'center',
    marginBottom: 12,
  },
  buttonPrimary: {
    backgroundColor: '#3498DB',
  },
  buttonSecondary: {
    backgroundColor: '#FFFFFF',
    borderWidth: 1,
    borderColor: '#3498DB',
    margin: 5,
    flex: 1,
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: '600',
  },
  buttonTextSecondary: {
    color: '#3498DB',
    fontSize: 16,
    fontWeight: '600',
  },
});

export default CadastroUsuarioScreen;