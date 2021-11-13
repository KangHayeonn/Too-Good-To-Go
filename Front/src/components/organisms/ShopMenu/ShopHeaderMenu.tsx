import React from "react";
import styled from "@emotion/styled";
import MenuAtom from "../../atoms/MenuButton/MenuAtom";

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
			<MenuAtom to="" menuName="전체보기" />
			<MenuAtom to="한식" menuName="한식" />
			<MenuAtom to="분식" menuName="분식" />
			<MenuAtom to="야식" menuName="야식" />
			<MenuAtom to="양식" menuName="양식" />
			<MenuAtom to="일식" menuName="일식" />
			<MenuAtom to="중식" menuName="중식" />
			<MenuAtom to="패스트푸드" menuName="패스트푸드">
				<DropMenu>
					<MenuAtom to="치킨" menuName="치킨" />
					<MenuAtom to="피자" menuName="피자" />
				</DropMenu>
			</MenuAtom>
			<MenuAtom to="찜탕" menuName="찜 · 탕" />
			<MenuAtom top to="디저트" menuName="디저트" />
		</HeaderMenu>
	);
};

export default ShopHeaderMenu;
