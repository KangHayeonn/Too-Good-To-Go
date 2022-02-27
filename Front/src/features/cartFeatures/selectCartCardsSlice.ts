// rtk
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { shopData3 } from "../../components/molecules/ShopDummyData";
// localStorage helper function
import { setLocalStorageCart } from "../../helpers/cartControl";

export type CartCardType = {
	id: number;
	name: string;
	image: string;
	discountedPrice: number;
	price: number;
	shopId: number;
	shopName: string;
};

export type initialCartCardType = CartCardType & {
	isChecked: boolean;
	cartItemQuantity: number;
};

const initialCards: initialCartCardType[] = shopData3.map((e: CartCardType) => {
	return { ...e, isChecked: false, cartItemQuantity: 1 };
});

export const selectCartCardsSlice = createSlice({
	name: "selectCartCards",
	initialState: initialCards,
	reducers: {
		selectCartCardByID: (state, action: PayloadAction<number>) => {
			const cards = state.map((e) => {
				if (e.id === action.payload) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});
			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(cards));

			return cards;
		},
		selectAllCartCards: (state, action: PayloadAction<boolean>) => {
			if (action.payload === true) {
				const cards = state.map((e) => {
					return { ...e, isChecked: false };
				});
				// set localstorage with state.selectCartCards
				setLocalStorageCart(JSON.stringify(cards));

				return cards;
				// eslint-disable-next-line no-else-return
			} else if (action.payload === false) {
				const cards = state.map((e) => {
					return { ...e, isChecked: true };
				});
				// set localstorage with state.selectCartCards
				setLocalStorageCart(JSON.stringify(cards));

				return cards;
			} else {
				return state;
			}
		},
		deleteSelectedCards: (state) => {
			let filteredArrOfState: initialCartCardType[] = [];
			console.log("state", state);
			filteredArrOfState = state.filter((e) => {
				return e.isChecked === false;
			});
			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(filteredArrOfState));

			return filteredArrOfState;
		},
		incrementSelectedCards: (state, action: PayloadAction<number>) => {
			const cards = state.map((e) => {
				if (e.id === action.payload) {
					return { ...e, cartItemQuantity: e.cartItemQuantity + 1 };
				}
				return e;
			});

			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(cards));

			return cards;
		},
		decrementSelectedCards: (state, action: PayloadAction<number>) => {
			// Deletes card which has cartItemQuantity <= 0
			const cards = state
				.map((e) => {
					if (e.id === action.payload) {
						return {
							...e,
							cartItemQuantity: e.cartItemQuantity - 1,
						};
					}
					return e;
				})
				.filter((e) => {
					return e.cartItemQuantity;
				});

			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(cards));

			return cards;
		},
		addItemsToCart: (
			state,
			action: PayloadAction<initialCartCardType[]>
		) => {
			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(action.payload));

			return action.payload;
		},
	},
});

// Action creators are generated for each case reducer function
export const {
	selectCartCardByID,
	selectAllCartCards,
	deleteSelectedCards,
	incrementSelectedCards,
	decrementSelectedCards,
	addItemsToCart,
} = selectCartCardsSlice.actions;

export default selectCartCardsSlice.reducer;
