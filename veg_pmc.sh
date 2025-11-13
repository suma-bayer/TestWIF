export AWS_ACCOUNT="859924236380"
export AWS_ROLE="standard-user"
export AWS_REPO="datahub-csw-repo-vegpmc"

#source $(which edh-aws-auth.sh -p saml)
aws-sso -p saml --account $AWS_ACCOUNT --role $AWS_ROLE

#cd into temp directory 
#cd ~/csw-temp-docker/np/veg-pmc-producers/

#login
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $AWS_ACCOUNT.dkr.ecr.us-east-1.amazonaws.com

#docker build
#docker build -t $AWS_REPO .
docker buildx build --platform=linux/amd64 -t $AWS_REPO .

#docker tag
docker tag "$AWS_REPO":latest $AWS_ACCOUNT.dkr.ecr.us-east-1.amazonaws.com/"$AWS_REPO":latest

#docker push
docker push $AWS_ACCOUNT.dkr.ecr.us-east-1.amazonaws.com/"$AWS_REPO":latest