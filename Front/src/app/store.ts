import { configureStore } from "@reduxjs/toolkit";
import cartCardSelectorReducer from "../features/cartFeatures/selectCartCardsSlice";

export const store = configureStore({
	reducer: {
		selectCartCards: cartCardSelectorReducer,
	},
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;
