import { Dispatch } from "redux";
import { ActionType } from "../action-types";

export const checkCartItem = (shopId: number) => {
	return (dispatch: Dispatch) => {
		dispatch({
			type: ActionType.CHECK,
			payload: {
				id: shopId,
			},
		});
	};
};
