import React from "react";
import styled from "@emotion/styled";
import CategoriesAtom from "../../atoms/MenuButton/CategoriesAtom";

const HeaderMenu = styled.ul`
	display: flex;
	width: 100%;
	height: auto;
`;

const DropMenu = styled.ul`
	display: none;
	position: absolute;
	margin-top: 40px;
	flex-direction: column;
	background-color: #fff;
	width: 118px;
`;

const ShopHeaderMenu: React.FC = () => {
	return (
		<HeaderMenu>
			<CategoriesAtom to="" menuName="전체보기" />
			<CategoriesAtom to="한식" menuName="한식" />
			<CategoriesAtom to="분식" menuName="분식" />
			<CategoriesAtom to="야식" menuName="야식" />
			<CategoriesAtom to="양식" menuName="양식" />
			<CategoriesAtom to="일식" menuName="일식" />
			<CategoriesAtom to="중식" menuName="중식" />
			<CategoriesAtom to="패스트푸드" menuName="패스트푸드">
				<DropMenu>
					<CategoriesAtom to="치킨" menuName="치킨" />
					<CategoriesAtom to="피자" menuName="피자" />
				</DropMenu>
			</CategoriesAtom>
			<CategoriesAtom to="찜탕" menuName="찜 · 탕" />
			<CategoriesAtom to="디저트" menuName="디저트" />
		</HeaderMenu>
	);
};

export default ShopHeaderMenu;
