/* eslint-disable react/jsx-props-no-spreading */
import React, { useState } from "react";
import styled from "@emotion/styled";
import ShopEditModal from "../molecules/Shop/ShopEditModal";

const Wrapper = styled.div`
	display: flex;
	flex-direction: row; // 세로방향
	width: 1115px;
	margin: 0 auto;
	align-items: center;
	justify-content: flex-start;
	box-shadow: 0px 1px 6px rgba(0, 0, 0, 0.2);
	padding: 1.7em;
	.select-btn {
		width: 64px;
		height: 27px;
		border-radius: 8px;
		background-color: #cfcfcf;
		color: black;
		font-weight: 600;
		font-size: 13px;
		margin-left: 20px;
	}
`;

const ShopImage = styled.img`
	width: 165px;
	height: 170px;
	background-color: green;
	display: flex;
	justify-content: center;
	align-items: center;
	margin-right: 1.7em;
`;

const ShopTitle = styled.div`
	height: 40px;
	font-size: 29px;
	font-weight: 600;
	display: flex;
	align-items: flex-end;
	margin-top: -3px;
`;

const ShopAddress = styled.div`
	height: 30px;
	font-size: 27px;
	font-weight: 600;
	display: flex;
	align-items: flex-end;
	margin-top: 20px;
`;
const ItemCard = styled.div`
	height: 30px;
	font-size: 27px;
	font-weight: 600;
	display: flex;
	align-items: flex-end;
	margin-right: 10px;
`;
const ItemsCard = styled.div`
	height: 30px;
	font-size: 27px;
	font-weight: 600;
	display: flex;
	align-items: center;
`;

const ItemTitle = styled.div`
	height: 30px;
	font-size: 16px;
	display: flex;
	align-items: flex-end;
	font-weight: 500;
	color: #747474;
`;

const ItemContent = styled.div`
	height: 30px;
	font-size: 16px;
	display: flex;
	align-items: flex-end;
	font-weight: 500;
	color: #b4b4b4;
	margin: 0 10px;
`;

const Content = styled.section`
	width: auto;
	//box-sizing: border-box; /* box사이즈를 기준으로 요소의 너비와 높이를 계산 */
	//margin: 1px auto;
	padding: 1.5em;
	display: flex;
	flex-direction: column;
`;

const TitleWrap = styled.div`
	display: flex;
	align-items: center;
`;

type Props = {
	image: string; // string 이나 이미지는 어떻게 넣지,,,
	title: string;
	time: string;
	address: string;
	category: Array<string>;
	hours: {
		open: string;
		close: string;
	};
	phone: string;
	isEdit: boolean;
};

const TitleTemplate: React.FC<Props> = ({
	image,
	title,
	time,
	address,
	category,
	hours,
	phone,
	isEdit,
	...props
}) => {
	const [isModal, setIsModal] = useState(false);
	const handleModal = () => {
		setIsModal(!isModal);
	};
	return (
		<Wrapper {...props}>
			<ShopImage src={image} alt="ShopImage" />
			<Content>
				<TitleWrap>
					<ShopTitle>{title}</ShopTitle>
					{isEdit ? (
						<>
							<button
								className="select-btn"
								type="button"
								onClick={handleModal}
							>
								수정
							</button>
							{!!isModal && (
								<ShopEditModal
									modal={handleModal}
									shopName={title}
									shopAddress={address}
									shopCategory={category.toString()}
									shopTel={phone}
									shopOpen={hours.open}
									shopClose={hours.close}
								/>
							)}
						</>
					) : null}
				</TitleWrap>
				<ShopAddress>
					<ItemTitle>주소</ItemTitle>
					<ItemContent>{address}</ItemContent>
				</ShopAddress>
				<ItemsCard>
					<ItemCard>
						<ItemTitle>영업시간</ItemTitle>
						<ItemContent>{time}</ItemContent>
					</ItemCard>
					<ItemCard>
						<ItemTitle>전화번호</ItemTitle>
						<ItemContent>{phone}</ItemContent>
					</ItemCard>
				</ItemsCard>
			</Content>
		</Wrapper>
	);
};

export default TitleTemplate;
