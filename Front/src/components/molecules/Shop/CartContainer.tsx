import React from "react";
import styled from "@emotion/styled";
import CloseIcon from "@mui/icons-material/Close";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../../app/store";
// rtk
import {
	incrementSelectedItems,
	decrementSelectedItems,
	deleteSelectedItem
} from "../../../features/shopFeatures/selectMenuItemsSlice";

type buttonType = {
	children: React.ReactNode;
};

const CartContainer: React.FC<buttonType> = ({ children }) => {
	const dispatch = useDispatch();

    const isCheckedArr = useSelector((state: RootState) => {
		return state.selectMenuItems.filter((e) => {
			return e.isChecked;
		});
	});

	// Accumulate money for display
	const accumulatedAmount: number = isCheckedArr.reduce((accu, curr) => {
		// eslint-disable-next-line no-param-reassign
		accu += curr.shopFoodCost * curr.cartItemQuantity;
		return accu;
	}, 0);

	// Insert thousand comma separator from accumulatedAmount, IIFE used.
	const numberWithCommas = ((x: number) => {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	})(accumulatedAmount);

	return (
		<Wrapper>
			<div className="first-section">
				<p hidden>1</p>
			</div>
			<div className="second-section">
				<p className="menu">
					{isCheckedArr.map((e) => {
						return (
                            <div className="menu-item">
								<Item>
									<li key={e.shopId}>
									{e.shopFoodName}
									{e.cartItemQuantity > 1 &&
										` x ${e.cartItemQuantity}`}
									</li>
									<button
										className="delete"
										type ="button"
										onClick={() => {
											dispatch(deleteSelectedItem(e.shopId));
										}}
									>
										<CloseIcon className="delete-icon" />
									</button>
								</Item>
                                <QuantityButton>
										<button
                                            className="menu-item-button"
											type="button"
											onClick={() => {
												dispatch(
													decrementSelectedItems(
														e.shopId
													)
												);
											}}
										>
											-
										</button>
										<p>{e.cartItemQuantity}</p>
										<button
                                            className="menu-item-button"
											type="button"
											onClick={() => {
												dispatch(
													incrementSelectedItems(
														e.shopId
													)
												);
											}}
										>
											+
										</button>
									</QuantityButton>
                            </div>
                        )
					})}
				</p>
			</div>
			<div className="third-section">
				<p>총 {numberWithCommas}원</p>
			</div>
			<div className="fourth-section">
				<button type="button">{children}</button>
			</div>
		</Wrapper>
	);
};

export default CartContainer;

const Wrapper = styled.div`
	width: 271px;
	min-height: 349px;
	height: auto;
	border: 1px solid lightgrey;
	display: flex;
	flex-direction: column;
	justify-content: space-between;

	.first-section {
		height: 58px;
		background-color: #54b689;
	}

	.second-section {
		min-height: 99px;
		height: auto;
		border-bottom: 2px solid grey;
		display: flex;
		justify-content: center;
		align-items: flex-start;
		flex-direction: column;
		p {
			margin-left: 20px;
			margin: 10px;
			li {
				margin: 5px;
				list-style: none;
				text-align: left;
			}
		}
		.menu {
			font-size: 20px;
			font-weight: bold;
		}
		.menu-item {
            border-bottom: 1px solid #eee;
			margin-bottom: 10px;
		}
	}
	.third-section {
		height: 47px;
		border-bottom: 2px solid grey;
		color: #54b689;
		font-weight: 700;
		font-size: 20px;
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}
	p {
		margin-right: 15px;
	}
	.fourth-section {
		height: 81px;
	}

	button {
		width: 152px;
		height: 45px;
		background: #54b689;
		color: white;
		font-size: 15px;
		font-weight: 700;
		border-radius: 12px;
		margin-top: 18px;
	}

	h1 {
		display: hidden;
	}
`;

const Item = styled.div`
	display:flex;
	justify-content: space-between;

	.delete {
		background: white;
		color: black;
		width : 33px;
		height: 19px;
		margin-top: 6px;
	}
	.delete-icon {
		font-size: 19px;
	}
`

const QuantityButton = styled.div`
	width: 80px;
	height: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-direction: row;
	overflow: hidden;
	border-radius: 13px;
	background-color: #e7e4e4;
	margin: 10px 0 13px 170px;

	.menu-item-button {
		font-size: 17px;
		width: 30px;
		background-color: #e7e4e4;
        padding-bottom: 21px;
        color: #959595;
	}

    p {
        font-size: 14px;
    }
`;