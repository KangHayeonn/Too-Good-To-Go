<<<<<<< HEAD
export const setSessionToken = (token: string): void => {
	sessionStorage.setItem("accessToken", token);
};

export const getSessionToken = (): string | null => {
	return sessionStorage.getItem("accessToken");
};

=======
export const setAccessToken = (token: string): void => {
	localStorage.setItem("accessToken", token);
};

export const getAccessToken = (): string | null => {
	return localStorage.getItem("accessToken");
};

export const deleteAccessToken = (): void => {
	return localStorage.removeItem("accessToken");
};


>>>>>>> master
// export const LocalStorageHandler = {
// 	set : setSessionToken,
// 	get : getSessionToken,

<<<<<<< HEAD
// }
=======
// }
>>>>>>> master
