from flask import Flask, request, jsonify
from flask_cors import CORS
import mysql.connector
import google.generativeai as genai

app = Flask(__name__)
CORS(app)

# Gemini
genai.configure(api_key="AIzaSyDAjN9WiI--KNg2bQ1CuGMSVKnAFWWOQSc")
model = genai.GenerativeModel("gemini-flash-latest")

# DB connection (from your reference)
def get_db_connection():
    return mysql.connector.connect(
        host="localhost",
        user="root",
        password="prakhars",
        database="briefq"
    )

# 📌 Get all schemes
@app.route('/schemes', methods=['GET'])
def get_schemes():
    category = request.args.get('category')

    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)

    if category:
        cursor.execute("SELECT * FROM schemes WHERE category=%s", (category,))
    else:
        cursor.execute("SELECT * FROM schemes")

    data = cursor.fetchall()
    cursor.close()
    conn.close()

    return jsonify(data)

# 🤖 Summarize scheme
@app.route('/summarize', methods=['POST'])
def summarize():
    text = request.json.get("text")

    prompt = f"""
    Summarize this scheme in simple English and Hindi:
    {text}
    """

    response = model.generate_content(prompt)
    return jsonify({"summary": response.text})

# 💡 Suggest usage
@app.route('/suggest', methods=['POST'])
def suggest():
    try:
        data = request.get_json()

        text = data.get("text")
        category = data.get("category")

        prompt = f"""
        User Category: {category}

        Based on the following information, give personalized advice:
        - How to use schemes
        - Practical steps
        - Tips for maximum benefit

        Keep it simple and useful.

        Content:
        {text}
        """

        response = model.generate_content(prompt)

        return jsonify({"advice": response.text})

    except Exception as e:
        print("ERROR:", str(e))
        return jsonify({"error": str(e)}), 500

# 💬 Chatbot
@app.route('/chat', methods=['POST'])
def chat():
    try:
        data = request.get_json()

        message = data.get("message")
        category = data.get("category", "")

        if not message:
            return jsonify({"error": "Empty message"}), 400

        # 🎯 CONTEXT PROMPT
        prompt = f"""
        The user belongs to category: {category}.

        Answer the question keeping this in mind.
        If category is 'students', give answers useful for students.
        If 'working', focus on professionals.
        If 'pension', focus on retired people.

        Question: {message}
        """

        response = model.generate_content(prompt)

        return jsonify({"reply": response.text})

    except Exception as e:
        print("ERROR:", str(e))
        return jsonify({"error": str(e)}), 500
    
if __name__ == '__main__':
    app.run(debug=True)
