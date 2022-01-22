import React from "react";
// Styles
import styled from "@emotion/styled";
// redux
import { useDispatch, useSelector } from "react-redux";
// rtk
import {
	selectCartCardByID,
	initialCartCardType,
} from "../../../features/shopFeatures/selectMenuItemsSlice";
import { RootState } from "../../../app/store";
// images
import fighting from "../../../../public/image/화이팅도치.jpg";

// Emotion theme
type theme = {
	checked: boolean;
};

// 할인율 계산
const calculatedDiscount = (price: number, discountedPrice: number): number => {
	return Math.ceil((1 - discountedPrice / price) * 100);
};

interface shopMatchId {
	shopMatchId?: number;
}
const MenuCards: React.FC<shopMatchId> = ({ shopMatchId }) => {
	const dispatch = useDispatch();
	console.log(shopMatchId);

	// logic to display cards
	const displayCardArr = useSelector((state: RootState) => {
		return state.selectMenuItems;
	});

	if (displayCardArr.length) {
		return (
			<Wrapper>
				{displayCardArr.map((card: initialCartCardType) => {
					return (
						// emotion conditional css
						<CartCard key={card.shopId} checked={card.isChecked}>
							<div className="card-img-ctn">
								<img src={card.shopFoodImg} alt="Food" />
							</div>
							<div className="cardInfo">
								<p className="cardInfo-shopName">
									{card.shopName}
								</p>
								<p className="cardInfo-shopFoodName">
									{card.shopFoodName}
								</p>
								<div className="cardInfo-price">
									<p>
										{calculatedDiscount(
											card.shopBeforeCost,
											card.shopFoodCost
										)}
										%
									</p>
									<p>{card.shopFoodCost}원</p>
									<s>({card.shopBeforeCost}원)</s>
								</div>
							</div>
							<div className="right-wrapper">
								<div className="btn-ctn">
									<button
										className="select-btn"
										type="button"
										onClick={() => {
											dispatch(
												selectCartCardByID(card.shopId)
											);
										}}
									>
										담기
									</button>
								</div>
							</div>
						</CartCard>
					);
				})}
			</Wrapper>
		);
	}
	return (
		<Wrapper>
			<img src={fighting} alt="화이팅도치의 존재" />
		</Wrapper>
	);
};

MenuCards.defaultProps = {
	shopMatchId: 0,
};

export default MenuCards;

const Wrapper = styled.div`
	width: 700px;
	height: 579px;
	border-top: 1px solid #d3d3d3;
	overflow: visible;
	overflow-x: hidden;
	padding: 30px 10px;
	margin-right: 25px;
	::-webkit-scrollbar {
		width: 10px;
	}
	::-webkit-scrollbar-thumb {
		background-color: #e7e4e4;
		border-radius: 12px;
		background-clip: padding-box;
		border: 2px solid transparent;
	}
	::-webkit-scrollbar-track {
		background-color: white;
		border-radius: 10px;
		box-shadow: inset 0px 0px 5px white;
	}
`;

const CartCard = styled.div<theme>`
	width: 660px;
	height: 181px;
	padding: 10px 10px 10px 0;
	margin: 25px 25px 20px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;
	border: ${({ checked }) =>
		checked ? `2px solid #C9C9C9` : `1px solid #d3d3d3`};
	box-shadow: ${({ checked }) =>
		checked
			? `0 4px 8px 0 rgba(0, 0, 0, 0.4)`
			: `0 4px 8px 0 rgba(0, 0, 0, 0.2)`};

	transition: 0.3s;
	:hover {
		box-shadow: ${({ checked }) =>
			checked
				? `0 8px 16px 0 rgba(0, 0, 0, 0.5)`
				: ` 0 8px 16px 0 rgba(0, 0, 0, 0.3)`};
	}

	.card-img-ctn {
		width: 134px;
	}

	img {
		width: 100%;
		overflow: hidden;
		margin-left: 14px;
		border-radius: 12px;
	}

	.cardInfo {
		display: flex;
		align-items: flex-start;
		flex-direction: column;
		padding-left: 20px;
	}

	.cardInfo > * {
		margin: 3px;
		margin-left: 14px;
	}

	.cardInfo-shopName {
		font-size: 16px;
	}

	.cardInfo-shopFoodName {
		font-size: 22px;
		font-weight: 600;
	}

	.cardInfo-price {
		display: flex;
		font-size: 17px;
		font-weight: 500;
	}

	.cardInfo-price > p:first-of-type {
		color: #2f8d09;
		padding-right: 5px;
		font-weight: 700;
	}

	p:last-child {
		font-weight: 600;
	}

	.right-wrapper {
		margin-left: 160px;
	}

	.btn-ctn {
		/* display: border-box; */
		flex-direction: row;
		width: 100px;
		display: flex;
		align-items: center;
		justify-content: center;
		margin: 10px;

		.select-btn {
			width: 99px;
			height: 27px;
			border-radius: 8px;
			background-color: #cfcfcf;
			color: black;
			font-weight: 600;
			font-size: 13px;
			margin: 3px;
		}
	}
`;
