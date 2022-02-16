// User_ID
export const setUserID = (id: string): void => {
	localStorage.setItem("user_id", id);
};

export const getUserID = (): string | null => {
	return localStorage.getItem("user_id");
};

export const deleteUserID = (): void => {
	return localStorage.removeItem("user_id");
};

// Email
export const setEmail = (email: string): void => {
	localStorage.setItem("email", email);
};

export const getEmail = (): string | null => {
	return localStorage.getItem("email");
};

export const deleteEmail = (): void => {
	return localStorage.removeItem("email");
};

// Name
export const setName = (name: string): void => {
	localStorage.setItem("name", name);
};

export const getName = (): string | null => {
	return localStorage.getItem("name");
};

export const deleteName = (): void => {
	return localStorage.removeItem("name");
};

// Phone
export const setPhone = (phone: string): void => {
	localStorage.setItem("phone", phone);
};

export const getPhone = (): string | null => {
	return localStorage.getItem("phone");
};

export const deletePhone = (): void => {
	return localStorage.removeItem("phone");
};

// Role
export const setRole = (role: string): void => {
	localStorage.setItem("role", role);
};

export const getRole = (): string | null => {
	return localStorage.getItem("role");
};

export const deleteRole = (): void => {
	return localStorage.removeItem("role");
};
