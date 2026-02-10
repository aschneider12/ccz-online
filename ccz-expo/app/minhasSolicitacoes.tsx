import SharedButton from '@/components/SharedButton';
import { API_URLS } from '@/config/api';
import { useUser } from '@/context/context';
import { IAlertaCidadao } from '@/interfaces/IAlertaCidadao';
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

  const [solicitacoes, setSolicitacoes] = useState<IAlertaCidadao[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    buscarSolicitacoes();
  }, []);

  async function buscarSolicitacoes() {
    try {
     
      console.log('buscando solicitacoes')
      const response = await fetch(API_URLS.ALERTA_CIDADAO.BASE+"?usuarioId="+user?.id);
      const data = await response.json();
      setSolicitacoes(data);

    } catch (error) {
      console.error('Erro ao buscar solicitações', error);
    } finally {
      setLoading(false);
    }
  }

  function renderItem({ item }: { item: IAlertaCidadao }) {
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
          data={solicitacoes}
          keyExtractor={(_, index) => index.toString()}
          renderItem={renderItem}
          contentContainerStyle={{ paddingBottom: 16 }}
          ListEmptyComponent={() => (
            <View>

            <Text style={styles.emptyText}>
              Você não realizou nenhuma solicitação.
              </Text>
               
            </View>
            
          )}
        />
         <SharedButton
              title="Nova"
              onPress={() => router.push("/cadastroSolicitacao")}
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
    flex: 1,
    flexDirection: 'row',
    marginLeft: 10,
  },
  actionButton: {
    padding: 6,
  },
});
