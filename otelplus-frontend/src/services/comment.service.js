import api from './api';

const CommentService = {
    addComment: async (otelId, commentData) => {
        // commentData: { kullaniciId, yorumIcerigi, puan, otelId }
        return await api.post(`/yorumlar/otel/${otelId}/yorumekle`, commentData);
    }
};

export default CommentService;
