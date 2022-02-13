import React from "react";
// Styles
import styled from "@emotion/styled";
// redux
import { useSelector } from "react-redux";
// rtk
import { initialCartCardType } from "../../../features/cartFeatures/selectCartCardsSlice";
import { RootState } from "../../../app/store";
// images
import fighting from "../../../../public/image/화이팅도치.jpg";
import CartCardEdit from "./CartCardEdit";

const CartCardsEdit: React.FC = () => {
	// logic to display cards
	const displayCardArr = useSelector((state: RootState) => {
		// Will return cards only with cartItemQuantity >= 1
		return state.selectCartCards.filter((e) => {
			return e.cartItemQuantity;
		});
	});

	if (displayCardArr.length) {
		return (
			<Wrapper>
				{displayCardArr.map((card: initialCartCardType) => {
					return (
						<CartCardEdit
							id={card.shopId}
							shopFoodImg={card.shopFoodImg}
							shopName={card.shopName}
							shopFoodName={card.shopFoodName}
							shopBeforeCost={card.shopBeforeCost}
							shopFoodCost={card.shopFoodCost}
						/>
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

export default CartCardsEdit;

const Wrapper = styled.div`
	width: 100%;
	height: 579px;
	/* border: 1px solid blue; */
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
