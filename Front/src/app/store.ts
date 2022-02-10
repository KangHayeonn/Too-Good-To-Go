import { configureStore } from "@reduxjs/toolkit";
import cartCardSelectorReducer from "../features/cartFeatures/selectCartCardsSlice";
import managerOrderCountReducer from "../features/manager/ManagerOrderCountSlice";
import menuItemsSelectorReducer from "../features/shopFeatures/selectMenuItemsSlice";
import updateMenuItemsReducer from "../features/shopFeatures/updateMenuItemsSlice";
import authReducer from "../features/auth/authSlice";
import userReducer from "../features/user/userSlice";

export const store = configureStore({
	reducer: {
		selectCartCards: cartCardSelectorReducer,
		managerOrderCount: managerOrderCountReducer,
		selectMenuItems: menuItemsSelectorReducer,
		updateMenuItems: updateMenuItemsReducer,
		auth: authReducer,
		user: userReducer,
	},
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;
