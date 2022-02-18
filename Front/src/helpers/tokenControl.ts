export const setAccessToken = (token: string): void => {
	localStorage.setItem("accessToken", token);
};

export const getAccessToken = (): string | null => {
	return localStorage.getItem("accessToken");
};

export const deleteAccessToken = (): void => {
	return localStorage.removeItem("accessToken");
};

export const setRefreshToken = (token: string): void => {
	localStorage.setItem("refreshToken", token);
};

export const getRefreshToken = (): string | null => {
	return localStorage.getItem("refreshToken");
};

export const deleteRefreshToken = (): void => {
	return localStorage.removeItem("refreshToken");
};
