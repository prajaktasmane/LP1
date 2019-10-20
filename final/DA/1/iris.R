
#reading the dataset
data=read.csv('iris.csv',header = FALSE,col.names = c("Sepal Length","Sepal Width","Petal Length","Petal Width","Species"))

#Print first 6 rows
head(data)

#Plot histogram for individual columns

hist(data$Sepal.Length,col="red")
hist(data$Sepal.Width,col="blue")
hist(data$Petal.Length,col="green")
hist(data$Petal.Width,col="yellow")

#hist(c(data$Sepal.Length,data$Sepal.Width,data$Petal.Length,data$Petal.Width))

#Boxplot for indiviual columns

boxplot(Sepal.Length~Species,data = data,xlab="Species",ylab="Sepal Length",col=c("violet","blue","purple"))

boxplot(Sepal.Width~Species,data = data,xlab="Species",ylab="Sepal Width",col=c("violet","blue","purple"))

boxplot(Petal.Length~Species,data = data,xlab="Species",ylab="Petal Length",col=c("violet","blue","purple"))

boxplot(Petal.Width~Species,data = data,xlab="Species",ylab="Petal Width",col=c("violet","blue","purple"))

#Combined boxplot
boxplot(data,data = data,xlab="Combined Plot",ylab="Value",col = "blue")

#Summary 
summary(data)

#For Individual columns
mean(data$Sepal.Length)
median(data$Sepal.Length)
sd(data$Sepal.Length)
var(data$Sepal.Length)

mean(data$Sepal.Width)
median(data$Sepal.Width)
sd(data$Sepal.Width)
var(data$Sepal.Width)

mean(data$Petal.Length)
median(data$Petal.Length)
sd(data$Petal.Length)
var(data$Petal.Length)

mean(data$Petal.Width)
median(data$Petal.Width)
sd(data$Petal.Width)
var(data$Petal.Width)
