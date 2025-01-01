import logging
from sentence_transformers import SentenceTransformer, util
import torch

# Configure logging
# logging.basicConfig(level=logging.INFO)


def calculate_similarity(model_path, sentence1, sentence2):
    """
    Calculates the semantic similarity between two sentences using a pre-trained SentenceTransformer model.

    Args:
        model_path: Path to the saved SentenceTransformer model.
        sentence1: The first sentence (string).
        sentence2: The second sentence (string).

    Returns:
        The cosine similarity score (float) between the two sentences, or None if an error occurs.  A higher score indicates greater similarity.
    """
    try:
        # Load the model
        model = SentenceTransformer(model_path)
        # logging.info(f"Loaded model from {model_path}")

        # Validate inputs
        if not isinstance(sentence1, str) or not isinstance(sentence2, str):
            raise ValueError("Both sentences must be strings.")
        if not sentence1 or not sentence2:
            raise ValueError("Sentences cannot be empty.")


        # Generate embeddings
        embeddings1 = model.encode(sentence1, convert_to_tensor=True)
        embeddings2 = model.encode(sentence2, convert_to_tensor=True)

        # Compute cosine similarity
        cosine_scores = util.cos_sim(embeddings1, embeddings2)
        similarity_score = cosine_scores.item()

        return similarity_score

    except FileNotFoundError:
        logging.error(f"Model file not found at {model_path}")
        return None
    except ValueError as e:
        logging.error(f"Error: {e}")
        return None
    except Exception as e:
        logging.exception(f"An unexpected error occurred: {e}")
        return None


# if __name__ == "__main__":
#     model_path = "Text_analysis\models\mpnet-base-all-nli-triplet\Final"  # Replace with your model path

#     sentence1 = input("Enter the first sentence: ")
#     sentence2 = input("Enter the second sentence: ")

#     similarity = calculate_similarity(model_path, sentence1, sentence2)

#     if similarity is not None:
#         print(f"Cosine similarity: {similarity:.4f}") #Print to 4 decimal places for better readability.
#     else:
#         print("Similarity calculation failed.")