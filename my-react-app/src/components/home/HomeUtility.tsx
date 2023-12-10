import axios, {AxiosResponse} from "axios";
import {checkJWTToken} from "../app/App";


class HomeUtility {
    static executeGet = (url: string): Promise<AxiosResponse> => {
        const jwtToken = checkJWTToken();
        if (jwtToken){
            const jwtToken = localStorage.getItem('jwtToken');
            axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
        }
        return axios.get(url)
    }
}
export default HomeUtility