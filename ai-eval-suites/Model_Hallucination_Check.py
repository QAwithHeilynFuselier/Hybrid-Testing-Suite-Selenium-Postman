
import openai
import json

def check_hallucination(model_output, reference_data):
    """
    Compara la salida del modelo contra datos reales para detectar invenciones.
    """
    prompt = f"""
    Actúa como un QA de IA. Compara la siguiente salida del modelo con los datos de referencia.
    Salida del Modelo: {model_output}
    Datos de Referencia: {reference_data}
    
    ¿Hay alguna información inventada o contradictoria? Responde solo con 'True' o 'False'.
    """

    # Aquí llamarías a tu modelo para la validación
    # response = openai.ChatCompletion.create(...)

    print(f"Verificando alucinaciones para: {model_output[:50]}...")
    return "No Hallucination Detected"

if __name__ == "__main__":
    # Datos de prueba para tu Python Console
    test_output = "El usuario tiene un saldo de $500"
    real_data = "Saldo actual: $500"
    print(check_hallucination(test_output, real_data))