import { Dispatch } from "redux";
import { ActionType } from "../action-types";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const checkCartItem = (shopId: number): any => {
	return (dispatch: Dispatch) => {
		dispatch({
			type: ActionType.CHECK,
			payload: {
				id: shopId,
			},
		});
	};
};
