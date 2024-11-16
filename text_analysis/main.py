from sentence_transformers import SentenceTransformer, util
import difflib
from typing import List

# Инициализация модели
model = SentenceTransformer('all-MiniLM-L6-v2')

# Интерфейс для анализа
class TextAnalyzer:
    def analyze(self, original: str, comparison: str) -> dict:
        pass

# Лексический анализ
class LexicalAnalyzer(TextAnalyzer):
    def analyze(self, original: str, comparison: str) -> dict:
        original_tokens = original.split()
        comparison_tokens = comparison.split()
        diff = difflib.ndiff(original_tokens, comparison_tokens)
        lexical_differences = [word[2:] for word in diff if word.startswith('- ')]
        return {"lexical_differences": lexical_differences}

# Синтаксический анализ
class SyntaxAnalyzer(TextAnalyzer):
    def analyze(self, original: str, comparison: str) -> dict:
        original_structure = [len(word) for word in original.split()]
        comparison_structure = [len(word) for word in comparison.split()]
        syntax_difference = original_structure != comparison_structure
        return {"syntax_difference": syntax_difference}

# Семантический анализ
class SemanticAnalyzer(TextAnalyzer):
    def analyze(self, original: str, comparison: str) -> dict:
        embedding1 = model.encode(original, convert_to_tensor=True)
        embedding2 = model.encode(comparison, convert_to_tensor=True)
        similarity_score = util.pytorch_cos_sim(embedding1, embedding2).item()
        return {"semantic_similarity": similarity_score}

# Анализ стиля
class StyleAnalyzer(TextAnalyzer):
    def analyze(self, original: str, comparison: str) -> dict:
        style_difference = abs(len(original) - len(comparison))
        return {"style_difference": style_difference}

# Класс для управления анализом
class DocumentComparison:
    def __init__(self, analyzers: List[TextAnalyzer]):
        self.analyzers = analyzers

    def compare(self, original: str, comparisons: List[str]) -> List[dict]:
        results = []
        for comparison in comparisons:
            analysis_result = {}
            for analyzer in self.analyzers:
                analysis_result.update(analyzer.analyze(original, comparison))
            results.append(analysis_result)
        return results

# Примеры данных
original_text = "My great sentence the Word at 2024"
comparison_sources = [
    ["My great sentence the Word at 2024", "My great sentenct the Word at", "My sentence the Word at 2024", "Great word at 2024"],
    ["My great sentence the Word", "My sentence at 2024", "Great Word 2024", "The Word 2024"],
    ["Great sentence at the Word", "My great Word at 2024", "Word the Great at 2024", "The sentence at Word"]
]

# Инициализация анализаторов
analyzers = [LexicalAnalyzer(), SyntaxAnalyzer(), SemanticAnalyzer(), StyleAnalyzer()]
comparison_tool = DocumentComparison(analyzers)

# Выполнение анализа для каждого источника
for source_index, source in enumerate(comparison_sources, start=1):
    print(f"\nАнализ для источника {source_index}:")
    results = comparison_tool.compare(original_text, source)
    for i, result in enumerate(results, start=1):
        print(f"\nСравнение предложения {i}:")
        print(result)