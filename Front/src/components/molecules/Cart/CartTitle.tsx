import React from "react";
import styled from "@emotion/styled";
import CartBreadcrumb from "../../atoms/Breadcrumb/CartBreadcrumb";

const CartTitle = () => {
	return (
		<TitleAndBreadcrumb>
			<CartText>장바구니</CartText>
			<CartBreadcrumb />
		</TitleAndBreadcrumb>
	);
};

const TitleAndBreadcrumb = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
`;

const CartText = styled.div`
	width: 158px;
	height: 65px;
	font-size: 40px;
	font-weight: bold;
	/* flex-direction: row; */
`;

export default CartTitle;
