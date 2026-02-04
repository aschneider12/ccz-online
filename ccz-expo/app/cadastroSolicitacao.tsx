import { showAlert } from '@/components/alert/AlertService';
import { API_URLS } from '@/config/api';
import { useUser } from '@/context/context';
import { IAlertaCidadao } from '@/interfaces/IAlertaCidadao';
import { IEspecie } from '@/interfaces/IEspecie';
import { IMunicipio } from '@/interfaces/IMunicipio';
import { ITipoNotificacao } from '@/interfaces/ITipoNotificacao';
import { Ionicons } from '@expo/vector-icons';
import DateTimePicker from '@react-native-community/datetimepicker';
import { Picker } from '@react-native-picker/picker';
import * as Location from 'expo-location';
import { Stack } from 'expo-router';
import React, { useEffect, useState } from 'react';

import {
  ActivityIndicator,
  Alert,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View
} from 'react-native';

const CadastroAlertaCidadaoScreen = () => {

  const { user } = useUser(); 

  const [formData, setFormData] = useState<IAlertaCidadao>({
    descricao: '',
    endereco: '',
    municipioId: '',
    tipoNotificacaoId: '',
    especieId: '',
    usuarioId: user?.id, 
    coordLatitude: '',
    coordLongitude: '',
    data: new Date(),
  });

  const [loading, setLoading] = useState(false);
  const [loadingLocation, setLoadingLocation] = useState(false);
  const [errors, setErrors] = useState({});
  const [showDatePicker, setShowDatePicker] = useState(false);
  const [useCurrentLocation, setUseCurrentLocation] = useState(false);


  // Listas para os selects
  const [tiposNotificacao, setTiposNotificacao] = useState<ITipoNotificacao[]>([]);

  const [municipios, setMunicipios] = useState<IMunicipio[]>([]);
  
  const [especies, setEspecies] = useState<IEspecie[]>([]);

  // Carregar dados dos selects
  useEffect(() => {
    carregarMunicipios();
    carregarTiposNotificacao();
    carregarEspecies();
  }, []);

  const carregarMunicipios = async () => {
    try {

      // Substitua pela chamada real à sua API
      const response = await fetch(API_URLS.MUNICIPIOS.BASE);
      const data = await response.json();
      setMunicipios(data);

    } catch (error) {
      showAlert("Problema request", 'Erro ao carregar municípios:', 'error');
      // Dados mockados para exemplo*/
      setMunicipios([
        { id: 1, descricao: 'Campo Grande', uf: 'MS' },
        { id: 2, descricao: 'Dourados', uf: 'MS' },
        { id: 3, descricao: 'Três Lagoas', uf: 'MS' },
      ]);
    }
  };

  const carregarTiposNotificacao = async () => {
    try {

      const response = await fetch(API_URLS.TIPOS_NOTIFICACAO.BASE);
      const data = await response.json();
      setTiposNotificacao(data);
      
    } catch (error) {

      console.error('Erro ao carregar tipos de notificação:', error);
      // Dados mockados*/
      setTiposNotificacao([
        { id: 1, descricao: 'Dengue - Urgente', urgencia: 3 },
        { id: 2, descricao: 'Escorpião - Alta', urgencia: 2 },
        { id: 3, descricao: 'Rato - Média', urgencia: 1 },
      ]);
    
    }
  };

  const carregarEspecies = async () => {
    try {

      const response = await fetch(API_URLS.ESPECIES.BASE);
      const data = await response.json();
      setEspecies(data);

    } catch (error) {
      
      showAlert("Dados das espécies não carregados", "Atenção", 'alert')

      console.error('Erro ao carregar espécies:', error);
      // Dados mockados*/
      setEspecies([
        { id: 1, descricao: 'Aedes aegypti (Mosquito da Dengue)' },
        { id: 2, descricao: 'Rattus norvegicus (Rato de esgoto)' },
        { id: 3, descricao: 'Tityus serrulatus (Escorpião amarelo)' },
      ]);
    }
  };

  // Obter localização atual
  const obterLocalizacaoAtual = async () => {
    setLoadingLocation(true);
    try {
      const { status } = await Location.requestForegroundPermissionsAsync();
     
      if (status !== 'granted') {
        
        showAlert('Permissão para acessar localização foi negada','Permissão Negada','alert');
        setLoadingLocation(false);
        return;
      }

      const location = await Location.getCurrentPositionAsync({
        accuracy: Location.Accuracy.High,
      });

      setFormData({
        ...formData,
        coordLatitude: location.coords.latitude.toFixed(6),
        coordLongitude: location.coords.longitude.toFixed(6),
      });

      Alert.alert('Sucesso', 'Localização obtida com sucesso!');

    } catch (error) {
      console.error('Erro ao obter localização:', error);
      Alert.alert('Erro', 'Não foi possível obter a localização');

    } finally {

      setLoadingLocation(false);
    }
  };

  // Validação do formulário
  const validarFormulario = () => {
    const novosErros = {};

    if (!formData.descricao.trim()) {
      novosErros.descricao = 'Descrição é obrigatória';
    } else if (formData.descricao.trim().length < 10) {
      novosErros.descricao = 'Descrição deve ter pelo menos 10 caracteres';
    }

    if (!formData.municipioId) {
      novosErros.municipioId = 'Município é obrigatório';
    }

    if (!formData.tipoNotificacaoId) {
      novosErros.tipoNotificacaoId = 'Tipo de notificação é obrigatório';
    }

    setErrors(novosErros);
    return Object.keys(novosErros).length === 0;
  };

  // Cadastrar alerta
  const cadastrarAlerta = async () => {
    if (!validarFormulario()) {
      showAlert('Por favor, verifique os erros no formulário', 'Atenção', 'alert');
      return;
    }

    setLoading(true);

    try {
      const payload = {
        descricao: formData.descricao,
        endereco: formData.endereco || null,
        municipioId: parseInt(formData.municipioId),
        tipoNotificacaoId: parseInt(formData.tipoNotificacaoId),
        especieId: formData.especieId ? parseInt(formData.especieId) : null,
        usuarioId: formData.usuarioId,
        coordLatitude: formData.coordLatitude ? parseFloat(formData.coordLatitude) : null,
        coordLongitude: formData.coordLongitude ? parseFloat(formData.coordLongitude) : null,
        data: formData.data.toISOString(),
      };

      const response = await fetch(API_URLS.ALERTA_CIDADAO.BASE, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      const data = await response.json();

      console.log('resposta do cadastro', response);
      console.log('dados da resposta', data);
      if (response.ok) {
        
        showAlert("Sucesso!", 'Alerta cadastrado com sucesso','success');
          
      } else {
        let mensagemErro = 'Erro ao cadastrar alerta';
        if (data.message) {
          mensagemErro = data.message;
        }
        showAlert("Erro cadastro",mensagemErro,'alert');
      }

    } catch (error) {
      
      console.error('Erro:', error);
      showAlert("Erro conexão",'Não foi possível conectar ao servidor','error');
      
    } finally {
      setLoading(false);
    }
  };

  const limparFormulario = () => {
    setFormData({
      descricao: '',
      endereco: '',
      municipioId: '',
      tipoNotificacaoId: '',
      especieId: '',
      usuarioId: 1,
      coordLatitude: '',
      coordLongitude: '',
      data: new Date(),
    });
    setErrors({});
    setUseCurrentLocation(false);
  };

  const onChangeDate = (event, selectedDate) => {
    setShowDatePicker(false);
    if (selectedDate) {
      setFormData({ ...formData, data: selectedDate });
    }
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
          <Stack.Screen options={{ title: 'voltar' }} />
          <Ionicons name="alert-circle" size={40} color="#E74C3C" />
          <Text style={styles.title}>Cadastrar alerta</Text>
          <Text style={styles.subtitle}>Reporte ocorrências de zoonoses nas região</Text>
        </View>

        <View style={styles.form}>
            <View style={styles.inputGroup}>
            <Text style={styles.label}>Solicitante: {user?.nome}</Text>
   
          </View>

          {/* Descrição */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Descrição *</Text>
            <TextInput
              style={[styles.textArea, errors.descricao && styles.inputError]}
              placeholder="Descreva o que você observou..."
              value={formData.descricao}
              onChangeText={(text) => setFormData({ ...formData, descricao: text })}
              multiline
              numberOfLines={4}
              maxLength={255}
            />
            {errors.descricao && (
              <Text style={styles.errorText}>{errors.descricao}</Text>
            )}
            <Text style={styles.helperText}>
              {formData.descricao.length}/255 caracteres
            </Text>
          </View>

          {/* Tipo de Notificação */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Tipo de ocorrência *</Text>
            <View style={[styles.pickerContainer, errors.tipoNotificacaoId && styles.inputError]}>
              
               <Picker
                selectedValue={formData.tipoNotificacaoId}
                onValueChange={(value) => setFormData({ ...formData, tipoNotificacaoId: value })}
                style={styles.picker}
              >
                <Picker.Item label="Selecione o tipo..." value="" />
                {tiposNotificacao?.map((tipo) => (
                  <Picker.Item
                    key={tipo.id}
                    label={tipo.descricao}
                    value={tipo.id}
                  />
                ))}
              </Picker>
            </View>
            {errors.tipoNotificacaoId && (
              <Text style={styles.errorText}>{errors.tipoNotificacaoId}</Text>
            )}
          </View>

          {/* Espécie */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Espécie observada (opcional)</Text>
            <View style={styles.pickerContainer}>
              <Picker
                selectedValue={formData.especieId}
                onValueChange={(value) => setFormData({ ...formData, especieId: value })}
                style={styles.picker}
              >
                <Picker.Item label="Não identificada" value="" />
                {especies?.map((especie) => (
                  <Picker.Item
                    key={especie.id}
                    label={especie.descricao}
                    value={especie.id}
                  />
                ))}
              </Picker>
            </View>
          </View>

          {/* Data/Hora */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Data e Hora *</Text>
            <TouchableOpacity
              style={styles.dateButton}
              onPress={() => setShowDatePicker(true)}
            >
              <Ionicons name="calendar" size={20} color="#3498DB" />
              <Text style={styles.dateText}>
                {formData.data.toLocaleString('pt-BR')}
              </Text>
            </TouchableOpacity>
            {showDatePicker && (
              <DateTimePicker
                value={formData.data}
                mode="datetime"
                is24Hour={true}
                display="default"
                onChange={onChangeDate}
              />
            )}
          </View>

          {/* Município */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Município *</Text>
            <View style={[styles.pickerContainer, errors.municipioId && styles.inputError]}>
              <Picker
                selectedValue={formData.municipioId}
                onValueChange={(value) => setFormData({ ...formData, municipioId: value })}
                style={styles.picker}
              >
                <Picker.Item label="Selecione o município..." value="" />
                {municipios?.map((municipio) => (
                  <Picker.Item
                    key={municipio.id}
                    label={`${municipio.descricao} - ${municipio.uf}`}
                    value={municipio.id}
                  />
                ))}
              </Picker>
            </View>
            {errors.municipioId && (
              <Text style={styles.errorText}>{errors.municipioId}</Text>
            )}
          </View>

          {/* Endereço */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Endereço (Opcional)</Text>
            <TextInput
              style={styles.input}
              placeholder="Rua, número, bairro..."
              value={formData.endereco}
              onChangeText={(text) => setFormData({ ...formData, endereco: text })}
              maxLength={255}
            />
          </View>

          {/* Localização */}
          <View style={styles.locationSection}>
            
            <View style={styles.locationHeader}>
              <Ionicons name="location" size={24} color="#3498DB" />
              <Text style={styles.locationTitle}>Localização GPS</Text>
            </View>

            <TouchableOpacity
              style={styles.locationButton}
              onPress={obterLocalizacaoAtual}
              disabled={loadingLocation}
            >
              {loadingLocation ? (
                <ActivityIndicator color="#FFF" />
              ) : (
                <>
                  <Ionicons name="navigate" size={20} color="#FFF" />
                  <Text style={styles.locationButtonText}>
                    Obter Localização Atual
                  </Text>
                </>
              )}
            </TouchableOpacity>

            <View style={styles.coordsRow}>
              <View style={[styles.coordInput, { marginRight: 8 }]}>
                <Text style={styles.coordLabel}>Latitude</Text>
                <TextInput
                  style={styles.input}
                  placeholder="Ex: -20.4486"
                  value={formData.coordLatitude}
                  onChangeText={(text) => setFormData({ ...formData, coordLatitude: text })}
                  keyboardType="numeric"
                />
              </View>

              <View style={styles.coordInput}>
                <Text style={styles.coordLabel}>Longitude</Text>
                <TextInput
                  style={styles.input}
                  placeholder="Ex: -54.6295"
                  value={formData.coordLongitude}
                  onChangeText={(text) => setFormData({ ...formData, coordLongitude: text })}
                  keyboardType="numeric"
                />
              </View>
            </View>
          </View>

          {/* Botões */}
          <View style={styles.buttonGroup}>
            <TouchableOpacity
              style={[styles.button, styles.buttonPrimary]}
              onPress={cadastrarAlerta}
              disabled={loading}
            >
              {loading ? (
                <ActivityIndicator color="#FFF" />
              ) : (
                <>
                  <Ionicons name="send" size={20} color="#FFF" />
                  <Text style={styles.buttonText}>Enviar alerta</Text>
                </>
              )}
            </TouchableOpacity>

            <TouchableOpacity
              style={[styles.button, styles.buttonSecondary]}
              onPress={limparFormulario}
              disabled={loading}
            >
              <Ionicons name="trash-outline" size={20} color="#E74C3C" />
              <Text style={styles.buttonTextSecondary}>Limpar</Text>
            </TouchableOpacity>
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
  scrollContent: {
    flexGrow: 1,
    padding: 20,
  },
  header: {
    alignItems: 'center',
    marginBottom: 24,
    marginTop: 20,    
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2C3E50',
    marginTop: 12,
    marginBottom: 4,
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
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 3.84,
    elevation: 5
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
  textArea: {
    backgroundColor: '#F8F9FA',
    borderWidth: 1,
    borderColor: '#E0E0E0',
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
    color: '#2C3E50',
    minHeight: 100,
    textAlignVertical: 'top',
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
  dateButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#F8F9FA',
    borderWidth: 1,
    borderColor: '#E0E0E0',
    borderRadius: 8,
    padding: 12,
  },
  dateText: {
    marginLeft: 10,
    fontSize: 16,
    color: '#2C3E50',
  },
  locationSection: {
    backgroundColor: '#EBF5FB',
    borderRadius: 8,
    padding: 16,
    marginBottom: 20,
  },
  locationHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 12,
  },
  locationTitle: {
    fontSize: 16,
    fontWeight: '600',
    color: '#2C3E50',
    marginLeft: 8,
  },
  locationButton: {
    flexDirection: 'row',
    backgroundColor: '#3498DB',
    borderRadius: 8,
    padding: 12,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 12,
  },
  locationButtonText: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: '600',
    marginLeft: 8,
  },
  coordsRow: {
    flexDirection: 'row',
  },
  coordInput: {
    flex: 1,
  },
  coordLabel: {
    fontSize: 14,
    color: '#7F8C8D',
    marginBottom: 4,
  },
  buttonGroup: {
    marginTop: 10,
  },
  button: {
    flexDirection: 'row',
    borderRadius: 8,
    padding: 16,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 12,
  },
  buttonPrimary: {
    backgroundColor: '#E74C3C',
  },
  buttonSecondary: {
    backgroundColor: '#FFFFFF',
    borderWidth: 1,
    borderColor: '#E74C3C',
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: '600',
    marginLeft: 8,
  },
  buttonTextSecondary: {
    color: '#E74C3C',
    fontSize: 16,
    fontWeight: '600',
    marginLeft: 8,
  },
});

export default CadastroAlertaCidadaoScreen;