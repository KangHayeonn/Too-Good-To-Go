import React from "react";
import styled from "@emotion/styled";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopHeaderMenu from "../../components/organisms/ShopMenu/ShopHeaderMenu";

const Cart = () => {
	return (
		<PageTemplate header={<ShopHeaderMenu />} isFooter>
			<Wrapper>d</Wrapper>
		</PageTemplate>
	);
};

export default Cart;

const Wrapper = styled.div`
	width: 100vw;
	height: 100vh;
`;
