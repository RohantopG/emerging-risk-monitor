import chromadb
from sentence_transformers import SentenceTransformer

# Initialize embedding model
model = SentenceTransformer("all-MiniLM-L6-v2")

# Create ChromaDB client
client = chromadb.Client()

# Create collection
collection = client.get_or_create_collection(name="risk_knowledge")


# 🔹 Add sample data (run once)
def load_data():
    documents = [
        "Cyber attacks in banking sector are increasing rapidly.",
        "Climate change is causing frequent floods and disasters.",
        "Stock market volatility is rising due to global tensions.",
        "AI risks include bias and lack of transparency."
    ]

    ids = [str(i) for i in range(len(documents))]

    embeddings = model.encode(documents).tolist()

    collection.add(
        documents=documents,
        embeddings=embeddings,
        ids=ids
    )


# 🔹 Query function
def query_data(text):
    query_embedding = model.encode([text]).tolist()

    results = collection.query(
        query_embeddings=query_embedding,
        n_results=2
    )

    return results["documents"][0]