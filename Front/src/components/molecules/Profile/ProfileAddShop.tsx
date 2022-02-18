import React from "react";
import styled from "@emotion/styled";
// MUI
import Button from "@mui/material/Button";
import Line13 from "../../../../public/image/Line 13.png";
import CategoryTag from "../../atoms/CategoryTag/CategoryTag";

const ProfileAddShop: React.FC = () => {
	return (
		<FormWrapper>
			<EditTitle className="edit-title">
				<p>매점 관리</p>
				<img src={Line13} alt="line13" />
				<p>SHOP</p>
			</EditTitle>
			<Wrapper>
				<form>
					<div className="section-wrapper">
						<div className="profile-info">가게 이름</div>
						<InputStyle
							name="productName"
							type="text"
							placeholder="가게 이름"
						/>
					</div>
					<div className="section-wrapper">
						<div className="profile-info">가게 이미지</div>
						<InputStyle
							name="image"
							type="file"
							accept="image/*"
							placeholder="상품 이미지"
						/>
					</div>

					<div className="section-wrapper category">
						<div className="profile-info">카테고리</div>
						<ul className="checkboxDiv">
							<li>
								<CategoryTag text="한식" />
							</li>
							<li>
								<CategoryTag text="분식" />
							</li>
							<li>
								<CategoryTag text="야식" />
							</li>
							<li>
								<CategoryTag text="양식" />
							</li>
							<li>
								<CategoryTag text="일식" />
							</li>
							<li>
								<CategoryTag text="중식" />
							</li>
							<li>
								<CategoryTag text="패스트푸드" />
							</li>
							<li>
								<CategoryTag text="치킨" />
							</li>
							<li>
								<CategoryTag text="피자" />
							</li>
							<li>
								<CategoryTag text="찜탕" />
							</li>
							<li>
								<CategoryTag text="디저트" />
							</li>
						</ul>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">전화번호</div>
						<InputStyle
							name="productName"
							type="text"
							placeholder="가게 전화번호"
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">주소</div>
						<InputStyle
							name="productName"
							type="text"
							placeholder="가게 주소"
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">영업 시간</div>
						<InputStyle
							name="productName"
							type="time"
							placeholder="상품 가격"
						/>
						~
						<InputStyle
							name="productName"
							type="time"
							placeholder="상품 할인 가격"
						/>
					</div>

					<Button variant="outlined" type="submit">
						가게 등록하기
					</Button>
				</form>
			</Wrapper>
		</FormWrapper>
	);
};

export default ProfileAddShop;

const FormWrapper = styled.div`
	position: relative;
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
`;

const EditTitle = styled.div`
	top: 26px;
	left: 37px;
	font-size: 20px;
	width: 350px;
	/* border: 1px solid purple; */
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin-top: 45px;

	p {
		font-size: 20px;
		font-weight: 700;
		color: #646464;
	}
`;

const InputStyle = styled.input`
	outline-style: none;
	border: 1px solid #999;
	width: auto;
`;

const Wrapper = styled.div`
	margin: 0;
	padding: 0;
	/* width: 800px; */
	/* min-height: 290px; */
	height: auto;
	/* border: 1px dashed black; */
	margin-top: 10px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	justify-content: space-between;

	form {
		margin-top: 30px;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		min-height: 489px;
		.section-wrapper {
			/* border: 1px dashed green; */
			width: 400px;
			height: 50px;
			justify-content: space-between;
			align-items: center;
			font-size: 18px;
			display: flex;
			flex-direction: row;
		}
		.category {
			height: 120px;
		}
	}

	.input {
		width: 284px;
		height: 47px;
		border-radius: 6px;
	}
	.checkboxDiv {
		width: 284px;
		padding: 0;
		li {
			letter-spacing: -0.009em;
			margin: 0 0.5rem 0.5rem 0;
			vertical-align: top;
			display: inline-block;
		}
	}

	button {
		/* position: relative; */
		/* top: 289px; */
		margin-top: 35px;
		width: 150px;
	}
`;
