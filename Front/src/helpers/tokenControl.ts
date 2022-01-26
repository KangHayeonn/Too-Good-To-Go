export const setSessionToken = (token: string): void => {
	sessionStorage.setItem("accessToken", token);
};

export const getSessionToken = (): string | null => {
	return sessionStorage.getItem("accessToken");
};

// export const LocalStorageHandler = {
// 	set : setSessionToken,
// 	get : getSessionToken,

// }
