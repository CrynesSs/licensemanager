import http from "../http-common.js"

class TutorialDataService {
    getAll() {
        return http.get("/tutorials");
    }

    get(id: any) {
        return http.get(`/tutorials/${id}`);
    }

    create(data: any) {
        return http.post("/tutorials", data);
    }

    update(id: any, data: any) {
        return http.put(`/tutorials/${id}`, data);
    }

    delete(id: any) {
        return http.delete(`/tutorials/${id}`);
    }

    deleteAll() {
        return http.delete(`/tutorials`);
    }

    findByTitle(title: any) {
        return http.get(`/tutorials?title=${title}`);
    }
}

export default new TutorialDataService();