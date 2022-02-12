import axios from "axios";
import {
	getAccessToken,
	getRefreshToken,
	setAccessToken,
} from "../helpers/tokenControl";

const BASE_URL = "http://54.180.134.20";

// 로그인 한 후에, api/me를 통해서 유저 정보를 받아오는 axios의 instance.
// 현재는 NavBar의 왼쪽편에 버튼으로 이어짐. 추후에 useEffect를 통해 다른곳에 놔야할듯
export const axiosApiMeGetInstance = axios.create({
	baseURL: BASE_URL,
	url: "/api/me",
	method: "GET",
	headers: {
		Authorization: `Bearer ${getAccessToken()}`,
		"Content-Type": "application/json",
	},
});

axiosApiMeGetInstance.interceptors.request.use(
	(config) => {
		const token = getAccessToken();
		console.log("interceptor token");
		console.log("interceptor config: ", config);
		if (token) {
			// eslint-disable-next-line no-param-reassign
			config.headers = {
				Authorization: `Bearer ${getAccessToken()}`,
				"Content-Type": "application/json",
			};
		}
		return config;
	},
	async (error) => {
		console.log("this is interceptor request error");
		console.error(error);
		return Promise.reject(error);
	}
);

// 항시 api/me
axiosApiMeGetInstance.interceptors.response.use(
	(res) => {
		console.log("this is interceptor.response res: ", res);
		return res;
	},
	async (error) => {
		console.log("this is interceptor.response.use error");
		console.error(error);

		// api/me를 얻는데, 실패했다면 실행할 스크립트.. refreshToken을 받아야해
		const originalConfig = error.config;
		if (error.response) {
			// Access Token was expired
			if (error.response.status === 401 && !originalConfig.retry) {
				originalConfig.retry = true;
				try {
					const responseFromRefreshToken =
						await getNewRefreshTokenPost();
					console.log(
						"response from refresh token: ",
						responseFromRefreshToken
					);
					const { accessToken, refreshToken } =
						responseFromRefreshToken.data.data;
					setAccessToken(accessToken);
					axiosApiMeGetInstance.defaults.headers.common.Authorization =
						accessToken;
					return axiosApiMeGetInstance(originalConfig);
					// eslint-disable-next-line @typescript-eslint/no-explicit-any
				} catch (error: any) {
					if (error.response && error.response.data) {
						return Promise.reject(error.response.data);
					}
					return Promise.reject(error);
				}
			}
		}

		return Promise.reject(error);
	}
);

function getNewRefreshTokenPost() {
	return axios.post("/api/reissue", {
		method: "post",
		headers: { Authorization: `Bearer ${getAccessToken()}` },
		data: {
			accessToken: getAccessToken(),
			refreshToken: getRefreshToken(),
		},
	});
}
