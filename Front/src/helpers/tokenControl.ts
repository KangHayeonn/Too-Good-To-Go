export const setAccessToken = (token: string): void => {
	localStorage.setItem("accessToken", token);
};

export const getAccessToken = (): string | null => {
	return localStorage.getItem("accessToken");
};

export const deleteAccessToken = (): void => {
	return localStorage.removeItem("accessToken");
};


// export const LocalStorageHandler = {
// 	set : setSessionToken,
// 	get : getSessionToken,

// }