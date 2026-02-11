import SharedButton from '@/components/SharedButton';
import { API_URLS } from '@/config/api';
import { useUser } from '@/context/context';
import { IAlertaCCZ } from '@/interfaces/IAlertaCCZ';
import { MaterialIcons } from '@expo/vector-icons';
import { router } from 'expo-router';
import React, { useEffect, useState } from 'react';
import {
  ActivityIndicator,
  FlatList,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';


export default function SolicitacoesScreen() {

  const { user } = useUser(); 

  const [meusAlertas, setAlertas] = useState<IAlertaCCZ[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    buscarMeusAlertas();
  }, []);

  async function buscarMeusAlertas() {
    try {
     
      const response = await fetch(API_URLS.ALERTTA_CCZ.BASE+"?usuarioId="+user?.id);
      const data = await response.json();
      setAlertas(data);

    } catch (error) {
      console.error('Erro ao buscar alertas', error);
    } finally {
      setLoading(false);
    }
  }

  function renderItem({ item }: { item: IAlertaCCZ }) {
    return (
      <View style={styles.card}>
        <Text style={styles.titulo}>{item.descricao}</Text>
        <Text style={styles.tipo}>{item.tipoNotificacaoId}</Text>
        <Text style={styles.data}>{item.data}</Text>
        
        <View style={styles.actions}>
          <TouchableOpacity
            onPress={() => handleEdit(item)}
            style={styles.actionButton}
          >
            <MaterialIcons name="edit" size={24} color="#4CAF50" />
          </TouchableOpacity>

          <TouchableOpacity
            onPress={() => handleDelete(item.id)}
            style={styles.actionButton}
          >
            <MaterialIcons name="delete" size={24} color="#F44336" />
          </TouchableOpacity>
        </View> 

      </View>
    );
  }

  if (loading) {
    return (
      <View style={styles.loading}>
        <ActivityIndicator size="large" color="#007AFF" />
      </View>
    );
  }

  return (

    <View style={styles.container}> 
        <FlatList
          data={meusAlertas}
          keyExtractor={(_, index) => index.toString()}
          renderItem={renderItem}
          contentContainerStyle={{ paddingBottom: 16 }}
          ListEmptyComponent={() => (
            <View>

            <Text style={styles.emptyText}>
              Você não emitiu nenhum alerta recente.
              </Text>
              
            </View>
            
          )}
        />
          <SharedButton
              title="Novo alerta"
              onPress={() => router.push("/cadastrarAlerta")}
              style={{ backgroundColor: '#007AFF' }}
              textStyle={{ fontSize: 20 }}
            />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
    padding: 16,
  },
  card: {
    backgroundColor: '#e2e9ed',
    padding: 16,
    borderRadius: 8,
    marginBottom: 12,
    elevation: 2,
  },
  titulo: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  tipo: {
    fontSize: 14,
    color: '#555',
  },
  data: {
    fontSize: 12,
    color: '#888',
    marginTop: 4,
  },
  loading: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
   emptyText: {
    fontSize: 16,
    color: '#999',
  },
  actions: {
    justifyContent: "space-between",
    alignItems: "center",
    marginLeft: 10,
  },
  actionButton: {
    padding: 4,
  },
});
