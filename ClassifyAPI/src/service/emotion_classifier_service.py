from transformers import pipeline

from src.domain.text_dto import TextDTO


class EmotionClassifierService:
    def __init__(self):
        self._classifier = pipeline(
            "sentiment-analysis",
            model="lxyuan/distilbert-base-multilingual-cased-sentiments-student"
        )

    def classify(self, text_dto: TextDTO) -> dict:
        result = self._classifier(text_dto.text)[0]
        return {
            "text": text_dto.text,
            "label": result["label"],
            "score": round(result["score"], 4)
        }

    def classify_all(self, text_dto_list: list[TextDTO]) -> list[dict]:
        responses: list[dict] = []
        for text_dto in text_dto_list:
            responses.append(self.classify(text_dto))
        return responses


