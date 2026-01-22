import React from "react";
import { StyleSheet, Text, TextInput, TextInputProps, View } from "react-native";

interface Props extends TextInputProps {
  label?: string;
}

export default function SharedTextInput({ label, style, ...props }: Props) {
  return (
    <View style={styles.wrapper}>
      {label && <Text style={styles.label}>{label}</Text>}
  
      <TextInput 
        style={[styles.input, style]}
        {...props}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  wrapper: {
    width: "100%",
    alignItems: "center",
  },
  label: {
    alignItems: "center",
    marginBottom: 5,
    fontSize: 16,
    fontWeight: "500"
  },
  input: {
    width: "90%",
    maxWidth: 400,         // ⬅️ Limite para não esticar no Web
    height: 45,
    borderWidth: 1,
    borderColor: "#ccc",
    borderRadius: 8,
    paddingHorizontal: 10,
    marginBottom: 15,
    fontSize: 16,
    backgroundColor: "#fff",
  },
});
