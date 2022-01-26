import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import Checkbox from "@mui/material/Checkbox";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Button from "@mui/material/Button";
// rtk
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../app/store";
import {
	deleteSelectedCards,
	selectAllCartCards,
} from "../../../features/cartFeatures/selectCartCardsSlice";

const CartSelectionButtons: React.FC = () => {
	const [selectAll, setSelectAll] = useState<boolean>(false);
	const dispatch = useDispatch();

	// Select All button logic.
	function handleSelectAll(e: React.MouseEvent<HTMLButtonElement>) {
		e.target as HTMLButtonElement;
		dispatch(selectAllCartCards(selectAll));
		setSelectAll(!selectAll);
	}

	const isCardsChecked: boolean = useSelector((state: RootState) => {
		// console.log(state.selectCartCards);
		return state.selectCartCards.some((e) => {
			return e.isChecked === false;
		});
	});

	useEffect(() => {
		if (!isCardsChecked) {
			return setSelectAll(true);
		}
		return setSelectAll(false);
	}, [isCardsChecked]);

	// Delete selected cards
	function handleDeleteCards() {
		dispatch(deleteSelectedCards());
	}

	// const state = useSelector((state: RootState) => {
	// 	return state.selectCartCards;
	// });

	// If no cards to display, turn off 전체선택 button off

	const isCardsPresent = useSelector((state: RootState) => {
		return state.selectCartCards;
	});

	useEffect(() => {
		if (!isCardsPresent.length) {
			setSelectAll(false);
		}
	}, [isCardsPresent]);

	return (
		<Wrapper>
			<FormGroup>
				<FormControlLabel
					control={
						<Checkbox
							onClick={(e) => {
								handleSelectAll(e);
							}}
							checked={selectAll}
						/>
					}
					label="전체 선택"
				/>
			</FormGroup>
			<Button
				variant="outlined"
				size="small"
				onClick={() => {
					handleDeleteCards();
				}}
			>
				삭제하기
			</Button>
		</Wrapper>
	);
};

export default CartSelectionButtons;

const Wrapper = styled.div`
	display: flex;
	width: 700px;
	height: 50px;
	/* border: 1px solid black; */
	justify-content: space-between;
	margin-top: 13px;
	margin-bottom: 9px;
	margin-left: 11px;
`;
