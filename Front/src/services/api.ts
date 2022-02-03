import axios from "axios";

const BASE_URL = "http://54.180.134.20";

const postRegisterData = (formData: string) => {
	return axios(`${BASE_URL}/api/signup`, {
		method: "post",
		data: formData,
		headers: { "Content-Type": "application/json" },
	});
};

const getBoard = () => {
	return axios.get(`${BASE_URL}/api/shopboardsa`);
};

const getShops = () => {
	return axios.get(`${BASE_URL}/api/shops`);
};

export default {
	postRegisterData,
	getBoard,
	getShops,
};
