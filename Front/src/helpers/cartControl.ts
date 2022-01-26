export const setLocalStorageCart = (token: string): void => {
	localStorage.setItem("cart", token);
};

export const getLocalStorageCart = (): string | null => {
	return localStorage.getItem("cart");
};
