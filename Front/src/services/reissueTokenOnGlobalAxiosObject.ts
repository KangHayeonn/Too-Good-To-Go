import axios from "axios";
import {
	getAccessToken,
	getRefreshToken,
	setAccessToken,
	setRefreshToken,
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

// accessToken 가져오기
axios.interceptors.request.use(
	(config) => {
		const token = getAccessToken();
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
		console.error(error);
		return Promise.reject(error);
	}
);

// axios + 2

// axios instance

// JWT를 줘야 하는 모든 axios request는, 이 라이브러리를 import해서, request를 보내라.
// jwtUsingAxiosRequestMakingFunction("http://xx.xx.xxx.xx/api/me"): axios객체 하나 + interceptors가 붙어있는 채로.

// function wtUsingAxiosRequestMakingFunction(url, method) => {
// 	return axios.method(
// 		baseUrl: url,
// 	)

// }

// axiosMakingInstancesThatDoesNotUseJWT(): axios객체 without Interceptors

// 항시 api/me
axios.interceptors.response.use(
	(res) => {
		return res;
	},
	async (error) => {
		console.error(error);

		// api/me를 얻는데, 실패했다면 실행할 스크립트.. refreshToken을 받아야해
		const originalConfig = error.config;
		if (error.response) {
			// Access Token was expired
			console.log(error.response.status, originalConfig.retry);
			if (error.response.status === 401 && !originalConfig.retry) {
				originalConfig.retry = true;
				try {
					const responseFromRefreshToken =
						await getNewRefreshTokenPost();
					const { accessToken, refreshToken } =
						responseFromRefreshToken.data.data;
					setAccessToken(accessToken);
					setRefreshToken(refreshToken);
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
	return axios.post(
		"/api/reissue",
		{
			accessToken: getAccessToken(),
			refreshToken: getRefreshToken(),
		},
		{ headers: { Authorization: `Bearer ${getAccessToken()}` } }
	);
}

// import axios
// axios.defaultInterceptor
// asycronous ==> pending, resolved, reject 이 세개가 다 Promise의 instance이다.
