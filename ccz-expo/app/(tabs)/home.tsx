import SharedButton from "@/components/SharedButton";
import { useRouter } from "expo-router";
import React from "react";
import { StyleSheet, Text, View } from "react-native";


export default function Home() {
  
    const router = useRouter();
 
 
  //const token = authContext.currentUser?.getIdTokenResult();
  //token?.then( (authObject) => {
   // console.log(authObject.token)
 // })
 
  
  return (

    <View style={styles.container}>

      <Text style={styles.text}>Bem vindo '~~NOME DO USUARIO~~'</Text>

      <SharedButton
          title="Solicitar auxílio"
          onPress={() => router.push("/cadastroSolicitacao")}
          style={{ backgroundColor: 'blue' }}
          textStyle={{ fontSize: 20 }}
        />

    </View>
    
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  text: {
    fontSize: 22,
    fontWeight: "bold",
  },
});
